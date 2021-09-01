package com.test.tpirates.controller;

import com.test.tpirates.etc.LunarCalendar;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GoodsControllerTest {

    @Test
    void createGoods() {
    }

    @Test
    void retrieveAllGoods() {
    }

    @Test
    void detailedGoods() {
    }

    @Test
    void receiptDates() {
    }

    @Test
    void deleteGoods() {
    }

    @Test
    void receiptCalc() throws ParseException {
        //given
        String type = "regular";
        String closing = "18:00";

        //when
        List<String> receiptDates = new ArrayList<>();
        int dateCount = 0;
        Integer closingTime = Integer.parseInt(closing.substring(0, 2));
        Integer nowTime = LocalTime.now().getHour();

        if(closingTime.compareTo(nowTime)<=0)
            ++dateCount;
        if (type.equals("regular"))
            ++dateCount;

        LocalDate scheduledDate = LocalDate.now().plusDays(dateCount);


//         공휴일 계산
//        LunarCalendar lunarCalendar = new LunarCalendar();
//        Set<String> holidayArray = lunarCalendar.holidayArray(String.valueOf(scheduledDate.getYear()));

        System.out.println("scheduledDate = " + scheduledDate);
        System.out.println("scheduledDate.getDayOfWeek() = " + scheduledDate.getDayOfWeek());

        while (scheduledDate.getDayOfWeek().name().equals("SATURDAY")||scheduledDate.getDayOfWeek().name().equals("SUNDAY")){
            LocalDate localDate = scheduledDate.plusDays(1);
            scheduledDate = localDate;
        }

        for (int i=0; i<5; i++){
            receiptDates.add(scheduledDate.plusDays(i).format(DateTimeFormatter.ofPattern("M월 d일 "))+scheduledDate.plusDays(i).getDayOfWeek().name());
        }


        //then
        System.out.println("수령일 " + receiptDates);
    }
}