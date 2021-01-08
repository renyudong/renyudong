package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    //查询
    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);

        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    //删除
    @Transactional //增删改方法用的注解
    @Override
    public Result<JsonObject> deleteCategoryById(Integer id) {

        //判断id是否合法
            if (null == id || id <0){
                return this.setResultError("id不合法");
            }

            //通过id查询当前节点的信息
            CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);

            //判断该数据是否存在
            if (categoryEntity == null ){
                return this.setResultError("数据不存在");
            }

            //判断当前节点是否为父节点
            if (categoryEntity.getIsParent() == 1){
                return this.setResultError("当前节点为父节点");
            }

            Example example1 = new Example(CategoryBrandEntity.class);
            example1.createCriteria().andEqualTo("categoryId",id);
            List<CategoryBrandEntity> list = categoryBrandMapper.selectByExample(example1);
            if(list.size() >= 1){
                return this.setResultError("下方有数据，不能删");
            }

            //根据当前节点信息中的父节点id查询，当前父节点下有其他节点
            Example example = new Example(CategoryEntity.class);
            example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());
            List<CategoryEntity> categoryList = categoryMapper.selectByExample(example);

            //假设size <= 1 那么将当前节点删除之后再将当前节点的父节点的状态改为0
            if (categoryList.size() <= 1){
                CategoryEntity updateCategoryEntity = new CategoryEntity();
                updateCategoryEntity.setIsParent(0);
                updateCategoryEntity.setId(categoryEntity.getParentId());

                categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);
            }

            //通过id删除节点
            categoryMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    //修改
    @Transactional
    @Override
    public Result<JsonObject> updateCategoryById(CategoryEntity categoryEntity) {

        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    //品牌新增
    @Transactional
    @Override
    public Result<JsonObject> addCategoryById(CategoryEntity categoryEntity) {

        CategoryEntity parentCategoryEntity = new CategoryEntity();
        parentCategoryEntity.setId(categoryEntity.getParentId());
        parentCategoryEntity.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(parentCategoryEntity);

        categoryMapper.insertSelective(categoryEntity);
        return this.setResultSuccess();
    }

    //回显
    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        //查询出商品的分类
        List<CategoryEntity> list = categoryMapper.getCategoryByBrandId(brandId);

        return this.setResultSuccess(list);
    }
}
