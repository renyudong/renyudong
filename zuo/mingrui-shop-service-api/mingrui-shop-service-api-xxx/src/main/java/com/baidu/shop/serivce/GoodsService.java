package com.baidu.shop.serivce;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SpuEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "商品接口")
public interface GoodsService {

    //查询
    @ApiOperation(value = "查询spu的数据")
    @GetMapping(value = "goods/list")
    Result<PageInfo<SpuDTO>> list(SpuDTO spuDTO);

    //新增
    @ApiOperation(value = "新增")
    @PostMapping(value = "goods/add")
    Result<JSONObject> addGoods(@Validated(MingruiOperation.Add.class) @RequestBody SpuDTO spuDTO);
}
