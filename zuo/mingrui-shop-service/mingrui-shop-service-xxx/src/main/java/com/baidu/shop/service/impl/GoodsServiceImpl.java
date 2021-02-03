package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.dto.SpuDetailDTO;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.serivce.GoodsService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/1/5
 * @Version V1.0
 **/
@RestController
public class GoodsServiceImpl extends BaseApiService implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;


    @Override
    public Result<PageInfo<SpuDTO>> list(SpuDTO spuDTO) {
        //分页
        if(spuDTO.getPage() != null && spuDTO.getRows() != null){
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());
        }

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(null != spuDTO.getSaleable() && spuDTO.getSaleable() < 2){
            criteria.andEqualTo("saleable",spuDTO.getSaleable());
        }
        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%" + spuDTO.getTitle() + "%");

        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);
        PageInfo<SpuEntity> spuEntityPageInfo = new PageInfo<>(spuEntities);
        return this.setResultSuccess(spuEntityPageInfo);
    }

    //新增
    @Override
    @Transactional
    public Result<JSONObject> addGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //新增spu，给部分字段复制，新增返回主键
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(spuEntity);

        //新增spuDetail
        SpuDetailDTO spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDetail,SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        //新增sku
        List<SkuDTO> list = spuDTO.getSkus();
        list.stream().forEach(skuDTO -> {

            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuEntity.getId());
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });

        return this.setResultSuccess();
    }

    //修改回显，查找spudetail，通过spuId查找
    @Override
    public Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId) {
        SpuDetailEntity spuDetailEntity = spuDetailMapper.selectByPrimaryKey(spuId);
        return this.setResultSuccess(spuDetailEntity);
    }

    //修改回显，查找sku,通过spuId查找
    @Override
    public Result<List<SkuDTO>> getSkusBySpuId(Integer spuId) {
        List<SkuDTO> list = skuMapper.getSkusBySpuId(spuId);
        return this.setResultSuccess(list);
    }

    //修改
    @Override
    @Transactional
    public Result<JSONObject> updateGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //修改spu
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        //修改spudetail
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDTO.getSpuDetail(),SpuDetailEntity.class);
        spuDetailMapper.updateByPrimaryKeySelective(spuDetailEntity);

        //通过spuId查询sku信息
        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuEntity.getId());
        List<SkuEntity> list = skuMapper.selectByExample(example);

        List<Long> skuIdList = list.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        skuMapper.deleteByIdList(skuIdList);//通过skuId集合删除sku信息
        stockMapper.deleteByIdList(skuIdList);//通过skuId集合删除stock信息

        //新增
        List<SkuDTO> list1 = spuDTO.getSkus();
        list1.stream().forEach(skuDTO -> {

            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuEntity.getId());
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> deleteGoods(Integer spuId) {
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);

        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> list = skuMapper.selectByExample(example);

        List<Long> skuIdList = list.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        skuMapper.deleteByIdList(skuIdList);//通过skuId集合删除sku信息
        stockMapper.deleteByIdList(skuIdList);//通过skuId集合删除stock信息


        return this.setResultSuccess();
    }
}
