package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecGroupService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
public class SpecGroupServiceImpl extends BaseApiService implements SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public Result<List<SpecGroupEntity>> getSpecGroup(SpecGroupDTO specGroupDTO) {

        Example example = new Example(SpecGroupEntity.class);
        example.createCriteria().andEqualTo("cid",
                BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }

    @Override
    @Transactional
    public Result<JSONObject> addSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> updateSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));

        return this.setResultSuccess();
    }


    @Override
    @Transactional
    public Result<JSONObject> deleteSpecGroup(Integer id) {
        //根据id查询数据
        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",id);

        List<SpecParamEntity> list = specParamMapper.selectByExample(example);
        if(list.size() >= 1){
            return this.setResultSuccess("下方有数据，不能删");
        }


        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }






    @Override
    public Result<List<SpecParamEntity>> getSpecParam(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        Example example = new Example(SpecParamEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(null != specParamEntity.getGroupId()){
            criteria.andEqualTo("groupId",specParamEntity.getGroupId());
        }

        if(null != specParamEntity.getCid()){
            criteria.andEqualTo("cid",specParamEntity.getCid());
        }


        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        return this.setResultSuccess(specParamEntities);
    }

    @Override
    @Transactional
    public Result<JSONObject> addSpecParam(SpecParamDTO specParamDTO) {
        specParamMapper.insertSelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> updateSpecParam(SpecParamDTO specParamDTO) {
        specParamMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> deleteSpecParam(Integer id) {
        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
}
