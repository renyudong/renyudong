package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.utils.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "规格接口")
public interface SpecGroupService {

    @GetMapping(value = "specgroup/list")
    @ApiOperation(value = "通过条件查询规格组")
    Result<List<SpecGroupEntity>> getSpecGroup(SpecGroupDTO specGroupDTO);

    @PostMapping(value = "specgroup/add")
    @ApiOperation(value = "新增")
    Result<JSONObject> addSpecGroup(@RequestBody SpecGroupDTO specGroupDTO);

    @PutMapping(value = "specgroup/add")
    @ApiOperation(value = "修改")
    Result<JSONObject> updateSpecGroup(@RequestBody SpecGroupDTO specGroupDTO);

    @DeleteMapping(value = "specgroup/delete")
    @ApiOperation(value = "删除")
    Result<JSONObject> deleteSpecGroup(Integer id);



    @ApiOperation(value = "通过条件查询规格参数")
    @GetMapping(value = "specparam/list")
    Result<List<SpecParamEntity>> getSpecParam(SpecParamDTO specParamDTO);

    @ApiOperation(value = "新增")
    @PostMapping(value = "specparam/add")
    Result<JSONUtil> addSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "修改")
    @PutMapping(value = "specparam/add")
    Result<JSONUtil> updateSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "specparam/delete")
    Result<JSONUtil> deleteSpecParam(Integer id);
}
