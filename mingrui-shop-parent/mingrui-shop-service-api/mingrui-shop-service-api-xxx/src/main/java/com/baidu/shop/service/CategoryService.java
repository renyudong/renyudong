package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品分类接口") //声明接口
public interface CategoryService<list> {

    //查询
    @ApiOperation(value = "通过pid查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);


    //删除
    @ApiOperation(value = "通过id删除分类")
    @DeleteMapping(value = "/category/delete")
    Result<JsonObject> deleteCategoryById(Integer id);


    //修改
    @ApiOperation(value = "通过id修改")
    @PutMapping(value = "/category/update")
    Result<JsonObject> updateCategoryById(@Validated({MingruiOperation.Update.class}) @RequestBody CategoryEntity categoryEntity);

    //新增
    @ApiOperation(value = "新增")
    @PostMapping(value = "/category/add")
    Result<JsonObject> addCategoryById(@Validated({MingruiOperation.Add.class}) @RequestBody CategoryEntity categoryEntity);


    //回显
    @ApiOperation(value = "通过品牌id查询商品分类")
    @GetMapping(value = "/category/brand")
    Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId);

}

