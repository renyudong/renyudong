package com.baidu.shop.serivce;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SpuEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "商品接口")
public interface GoodsService {

    //查询
    @ApiOperation(value = "查询spu的数据")
    @GetMapping(value = "goods/list")
    Result<PageInfo<SpuDTO>> list(SpuDTO spuDTO);
}
