package com.baidu.shop.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "tb_category_brand")
public class CategoryBrandEntity {

    private Integer categoryId;

    private Integer brandId;
}
