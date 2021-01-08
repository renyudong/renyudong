package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {

    //查询
    @GetMapping(value = "brand/list")
    @ApiOperation(value = "查询品牌列表")
    Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO);

    //新增
    @PostMapping(value = "brand/save")
    @ApiOperation(value = "增加")
    Result<JSONObject> addBrandInfo(@Validated(MingruiOperation.Add.class) @RequestBody BrandDTO brandDTO);

    //修改
    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改")
    Result<JSONObject> updateBrandInfo(@Validated(MingruiOperation.Update.class)@RequestBody BrandDTO brandDTO);

    //删除
    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "修改")
    Result<JSONObject> deleteBrandInfo(Integer id);

    //通过id查询品牌
    @GetMapping(value = "brand/getBrandByCategory")
    @ApiOperation(value = "通过分类id获取品牌")
    Result<List<BrandEntity>> getBrandByCategory(Integer cid);
}
