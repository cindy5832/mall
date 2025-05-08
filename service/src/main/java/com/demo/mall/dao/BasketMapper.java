package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.param.ShopCartParam;
import com.demo.mall.bean.model.Basket;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BasketMapper extends BaseMapper<Basket> {

    // 根據商品id獲得用戶id列表
    List<String> listUserIdsByProdId(@Param("prodId") Long prodId);

    // 獲取購物車列表
    List<ShopCartItemDto> getShopCartItems(@Param("userId") String userId);

    // 更新購物車滿額減免活動Id
    void updateDiscountItemId(@Param("userId") String userId, @Param("basketIdShopCartParamMap") Map<Long, ShopCartParam> basketIdShopCartParamMap);

    // 根據購物車Id和用戶id刪除購物車項目
    void deleteShopCartItemsByBasketIds(@Param("userId") String userId, @Param("basketIds") List<Long> basketIds);

    // 刪除購物車所有項目
    void deleteAllShopCartItems(@Param("userId") String userId);

    // 刪除失效的購物項
    void cleanExpiryProdList(@Param("userId") String userId);
}
