package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SpuDetailEntity;
import com.baidu.shop.entity.SpuEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品的接口")
public interface GoodsService {

    //查询
    @ApiOperation(value = "查询spu的数据")
    @GetMapping(value = "goods/list")
    Result<PageInfo<SpuDTO>> list(SpuDTO spuDTO);

    //新增
    @ApiOperation(value = "新增")
    @PostMapping(value = "goods/add")
    Result<JSONObject> addGoods(@Validated(MingruiOperation.Add.class) @RequestBody SpuDTO spuDTO);

    //回显，查找数据
    @ApiOperation(value = "通过spuId查询spudetail数据")
    @GetMapping(value = "goods/getSpuDetailBySpuId")
    Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId);

    //回显，查找数据
    @ApiOperation(value = "通过spuId查询spudetail数据")
    @GetMapping(value = "goods/getSkusBySpuId")
    Result<List<SkuDTO>> getSkusBySpuId(Integer spuId);

    //修改
    @ApiOperation(value = "修改")
    @PutMapping(value = "goods/add")
    Result<JSONObject> updateGoods(@Validated(MingruiOperation.Update.class) @RequestBody SpuDTO spuDTO);

    //删除
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "goods/delete")
    Result<JSONObject> deleteGoods(Integer spuId);

    //下架
    @ApiOperation(value = "将当前数据状态改为0")
    @GetMapping(value = "goods/xia")
    Result<JSONObject> xia(Integer spuId);

    //上架
    @ApiOperation(value = "将当前数据状态改为1")
    @GetMapping(value = "goods/shang")
    Result<JSONObject> shang(Integer spuId);
}
