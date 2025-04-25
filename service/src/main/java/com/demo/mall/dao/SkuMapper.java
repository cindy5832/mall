package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper extends BaseMapper<Sku> {


    // 根據商品id獲取商品sku
    List<Sku> listByProdId(Long prodId);

    // batch insert sku
    void insertBatch(@Param("prodId") Long prodId, @Param("skuList") List<Sku> skuList);

    // 根據商品id刪除sku
    void deleteByProdId(@Param("prodId") Long prodId);
}
