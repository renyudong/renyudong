package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.serivce.BrandService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.PinyinUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BrandServiceImpl extends BaseApiService implements BrandService {


    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO) {
        //分页
        PageHelper.startPage(brandDTO.getPage(),brandDTO.getRows());

        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO,BrandEntity.class);

        //排序
        if(!StringUtils.isEmpty(brandDTO.getSort())){//排序的字段不为空

            PageHelper.orderBy(brandDTO.getOrderBy());
        }


        Example example = new Example(BrandEntity.class);
        example.createCriteria().andLike("name","%" + brandEntity.getName() + "%");

        //查询
        List<BrandEntity> brandEntities = brandMapper.selectByExample(example);
        PageInfo<BrandEntity> pageInfo = new PageInfo<>(brandEntities);

        return this.setResultSuccess(pageInfo);
    }

    @Transactional
    @Override
    public Result<JSONObject> saveBrandInfo(BrandDTO brandDTO) {
        // char c = brandEntity.getName().toCharArray()[0]; //获得第一个字符
        // String s = String.valueOf(c); //转成String类型的数组
        // String upperCase = PinyinUtil.getUpperCase(s, false); //获取汉字首字母或全拼大写字母
        // char c1 = upperCase.toCharArray()[0]; //获得第一个字母
        // brandEntity.setLetter(c1);

        //新增返回主键?
        //两种方式实现 select-key insert加两个属性
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);

        //品牌首字母
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]), false).toCharArray()[0]);

        brandMapper.insertSelective(brandEntity);

        //维护中间表数据
        String categories = brandDTO.getCategories();//得到分类集合字符串

        if(StringUtils.isEmpty(brandDTO.getCategories())) {//数据不为空
            return this.setResultError("");
        }

        List<CategoryBrandEntity> categoryBrandEntities = new ArrayList<>();//定义list集合

        //判断分类集合字符串中是否包含,
        if(categories.contains(",")){//多个分类 --> 批量新增
            String[] categoryArr = categories.split(",");//根据逗号分割

            for (String s : categoryArr) {//遍历
                CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();//实体类
                categoryBrandEntity.setBrandId(brandEntity.getId());//获得品牌id
                categoryBrandEntity.setCategoryId(Integer.valueOf(s));//获得分类的数组
                categoryBrandEntities.add(categoryBrandEntity);//实体类给集合赋值
            }
            //insertListMapper
            categoryBrandMapper.insertList(categoryBrandEntities);
        }else{//普通单个新增

            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
            categoryBrandEntity.setBrandId(brandEntity.getId());
            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));

            categoryBrandMapper.insertSelective(categoryBrandEntity);
        }

        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> editBrandInfo(BrandDTO brandDTO) {
        //名字修改，首字母改变
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO,BrandEntity.class);
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]), false).toCharArray()[0]);
        brandMapper.updateByPrimaryKeySelective(brandEntity); //修改首字母

        //清空关系表中的数据
        Example example = new Example(CategoryBrandEntity.class);
        example.createCriteria().andEqualTo("brandId",brandDTO.getId());
        categoryBrandMapper.deleteByExample(example);

        String categories = brandDTO.getCategories();//得到分类集合字符串
        if(StringUtils.isEmpty(brandDTO.getCategories())) {//数据不为空
            return this.setResultError("");
        }

        List<CategoryBrandEntity> categoryBrandEntities = new ArrayList<>();//定义list集合
        //判断分类集合字符串中是否包含,
        if(categories.contains(",")){//多个分类 --> 批量新增
            String[] categoryArr = categories.split(",");//根据逗号分割

            for (String s : categoryArr) {//遍历
                CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();//实体类
                categoryBrandEntity.setBrandId(brandEntity.getId());//获得品牌id
                categoryBrandEntity.setCategoryId(Integer.valueOf(s));//获得分类的数组
                categoryBrandEntities.add(categoryBrandEntity);//实体类给集合赋值
            }
            //insertListMapper
            categoryBrandMapper.insertList(categoryBrandEntities);
        }else{//普通单个新增

            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
            categoryBrandEntity.setBrandId(brandEntity.getId());
            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));

            categoryBrandMapper.insertSelective(categoryBrandEntity);
        }

        return this.setResultSuccess();
    }

    //删除
    @Transactional
    @Override
    public Result<JSONObject> deleteBrandInfo(Integer id) {
        //删除id
        brandMapper.deleteByPrimaryKey(id);
        //删除关系表
        Example example = new Example(CategoryBrandEntity.class);
        example.createCriteria().andEqualTo("brandId",id);
        categoryBrandMapper.deleteByExample(example);//删除商品分类
        return this.setResultSuccess();
    }

    @Override
    public Result<List<BrandEntity>> getBrandByCategory(Integer cid) {
        List<BrandEntity> list = brandMapper.getBrandByCategory(cid);
        return this.setResultSuccess(list);
    }
}
