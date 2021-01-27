package com.baidu.shop.serivce;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "规格接口")
public interface SpecificationService {

    @ApiOperation(value = "通过条件查询规格组")
    @GetMapping(value = "specgroup/list")
    Result<List<SpecGroupEntity>> getSpecGroup(SpecGroupDTO specGroupDTO);

    @PostMapping(value = "specgroup/add")
    @ApiOperation(value = "新增")
    Result<JSONObject> addSpecGroup(@Validated(MingruiOperation.Add.class) @RequestBody SpecGroupDTO specGroupDTO);

    @PutMapping(value = "specgroup/add")
    @ApiOperation(value = "修改")
    Result<JSONObject> updateSpecGroup(@Validated(MingruiOperation.Update.class) @RequestBody SpecGroupDTO specGroupDTO);

    @DeleteMapping(value = "specgroup/delete")
    @ApiOperation(value = "删除")
    Result<JSONObject> deleteSpecGroup(Integer id);
}
