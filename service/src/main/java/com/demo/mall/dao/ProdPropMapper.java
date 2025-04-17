package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.ProdProp;
import com.demo.mall.common.utils.PageAdapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdPropMapper extends BaseMapper<ProdProp> {

    // 根據參數獲取商品屬性列表
    List<ProdProp> listPropAndValue(@Param("adapter") PageAdapter pageAdapter, @Param("prodProp") ProdProp prodProp);

    // 根據參數計算商品屬性
    long countPropAndValue(@Param("prodProp") ProdProp prodProp);

    // 根據商店id和屬性名稱獲取商品屬性
    ProdProp getProdPropByPropNameAndShopId(@Param("propName") String propName, @Param("shopId") Long shopId, @Param("rule") Integer rule);

    // 刪除商品屬性
    int deleteByPropId(@Param("propId") Long propId, @Param("rule") Integer propRule, @Param("shopId") Long shopId);
}
