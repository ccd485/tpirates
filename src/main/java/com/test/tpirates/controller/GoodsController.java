package com.test.tpirates.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.test.tpirates.domain.Delivery;
import com.test.tpirates.domain.Goods;
import com.test.tpirates.domain.Options;
import com.test.tpirates.exception.GoodsNotFoundException;
import com.test.tpirates.mappingInterface.GoodsMapping;
import com.test.tpirates.repository.DeliveryRepository;
import com.test.tpirates.repository.GoodsRepository;
import com.test.tpirates.repository.OptionsRepository;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    // 점포 추가 API
    @PostMapping("/goods")
    @Transactional
    public ResponseEntity<Goods> createGoods(@Valid @RequestBody Goods goods) {

        Goods savedGoods = goodsRepository.save(goods);

        Delivery delivery = savedGoods.getDelivery();
        delivery.setGoods(savedGoods);
        deliveryRepository.save(delivery);

        List<Options> options = savedGoods.getOptions();
        options.forEach(options1 -> options1.setGoods(savedGoods));
        optionsRepository.saveAll(options);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGoods.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // 상품 목록 조회 API
    @GetMapping("/goods")
    public List<GoodsMapping> retrieveAllGoods(){
        List<GoodsMapping> goodsList = goodsRepository.findGoods();

        return goodsList;
    }

    // 상품 상세 조회 API
    @GetMapping("/goods/{id}")
    public MappingJacksonValue detailedGoods(@PathVariable int id){

        Optional<Goods> goods = goodsRepository.findById(id);

        if (!goods.isPresent()) {
            throw new GoodsNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter_goods = SimpleBeanPropertyFilter
                .filterOutAllExcept("name","description","delivery","options");
        SimpleBeanPropertyFilter filter_delivery = SimpleBeanPropertyFilter
                .filterOutAllExcept("type");
        SimpleBeanPropertyFilter filter_Options = SimpleBeanPropertyFilter
                .filterOutAllExcept("name","price");

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("GoodsInfo",filter_goods)
                .addFilter("DeliveryInfo",filter_delivery)
                .addFilter("OptionsInfo",filter_Options);

        MappingJacksonValue mapping = new MappingJacksonValue(goods);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    // 수령일 선택 목록 API
    @GetMapping("/goods/{id}/receipt")
    public List<String> receiptDates(@PathVariable int id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        if (!goods.isPresent()) {
            throw new GoodsNotFoundException(String.format("ID[%s] not found", id));
        }

        String type = goods.get().getDelivery().getType();
        String closing = goods.get().getDelivery().getClosing();

        List<String> receiptDates = receiptCalc(type, closing);

        return receiptDates;
    }

    // 점포 삭제 API
    @DeleteMapping("/goods/{id}")
    public void deleteGoods(@PathVariable int id) {
        goodsRepository.deleteById(id);
    }

    // 수령일 계산 메소드
    public List<String> receiptCalc(String type, String closing){
        List<String> receiptDates = new ArrayList<>();
        int dateCount = 0;
        Integer closingTime = Integer.parseInt(closing.substring(0, 2));
        Integer nowTime = LocalTime.now().getHour();

        if(closingTime.compareTo(nowTime)<=0)
            ++dateCount;
        if (type.equals("regular"))
            ++dateCount;

        LocalDate scheduledDate = LocalDate.now().plusDays(dateCount);

        while (scheduledDate.getDayOfWeek().name().equals("SATURDAY")||scheduledDate.getDayOfWeek().name().equals("SUNDAY")){
            LocalDate localDate = scheduledDate.plusDays(1);
            scheduledDate = localDate;
        }

        for (int i=0; i<5; i++){
            receiptDates.add(scheduledDate.plusDays(i).format(DateTimeFormatter.ofPattern("M월 d일 "))+scheduledDate.plusDays(i).getDayOfWeek().name());
        }

        return receiptDates;
    }
}
