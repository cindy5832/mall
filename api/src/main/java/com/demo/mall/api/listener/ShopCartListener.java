package com.demo.mall.api.listener;

import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDiscountDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.event.ShopCartEvent;
import com.demo.mall.bean.order.ShopCartEventOrder;
import com.google.common.collect.Lists;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

// 默認的購物車鏈進行組裝時的操作
@Component("defaultShopCartListener")
public class ShopCartListener {
    // 將商店下的所有商品歸屬到該商店的購物車當中
    @EventListener(ShopCartEvent.class)
    @Order(ShopCartEventOrder.DEFAULT)
    public void shopCartEvent(ShopCartEvent event) {
        ShopCartDto shopCart = event.getShopCartDto();
        List<ShopCartItemDto> shopCartItemDtoList = event.getShopCartItemDtoList();
        // 對數據進行組裝
        List<ShopCartItemDiscountDto> shopCartItemDiscountDtoList = Lists.newArrayList();
        ShopCartItemDiscountDto shopCartItemDiscountDto = new ShopCartItemDiscountDto();

        shopCartItemDiscountDto.setShopCartItems(shopCartItemDtoList);
        shopCartItemDiscountDtoList.add(shopCartItemDiscountDto);
        shopCart.setShopCartItemDiscounts(shopCartItemDiscountDtoList);
    }
}
