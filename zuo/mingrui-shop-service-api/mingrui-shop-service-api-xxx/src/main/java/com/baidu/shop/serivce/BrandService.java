package com.baidu.shop.serivce;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BrandService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/12/25
 * @Version V1.0
 **/
@Api(tags = "品牌接口")
public interface BrandService {

    @GetMapping(value = "brand/list")
    @ApiOperation(value = "查询品牌列表")
    Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO);

    @PostMapping(value = "brand/save")
    @ApiOperation(value = "查询品牌列表")
    Result<JSONObject> saveBrandInfo(@RequestBody BrandDTO brandDTO);

    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改品牌")
    Result<JSONObject> editBrandInfo(@RequestBody BrandDTO brandDTO);

    //删除
    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "修改")
    Result<JSONObject> deleteBrandInfo(Integer id);

    //通过id查询品牌
    @GetMapping(value = "brand/getBrandByCategory")
    @ApiOperation(value = "通过分类id获取品牌")
    Result<List<BrandEntity>> getBrandByCategory(Integer cid);
}

