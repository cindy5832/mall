package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Basket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BasketMapper extends BaseMapper<Basket> {

    // 根據商品id獲得用戶id列表
    List<String> listUserIdsByProdId(@Param("prodId") Long prodId);
}
