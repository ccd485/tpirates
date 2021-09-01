package com.test.tpirates.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id","regDate"})
@JsonFilter("GoodsInfo")
@ApiModel(description = "상품 상세 정보를 위한 도메인 객체")
@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min=1, message = "Name은 1글자 이상 입력해주세요.")
    @ApiModelProperty(notes = "상품명")
    private String name;

    private String description;

    private Date regDate;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "goods")
    private Delivery delivery;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "goods")
    private List<Options> options;

    @PrePersist
    private void onCreate() {
        this.regDate = new Date();
    }

}
