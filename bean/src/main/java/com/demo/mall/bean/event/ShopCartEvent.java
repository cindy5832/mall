/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.event;

import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// 購物車商品改變事件
@Data
@AllArgsConstructor
public class ShopCartEvent {

    // 將要組裝的單個商店訊息
    private ShopCartDto shopCartDto;

    // 該商店下的所有商品訊息
    private List<ShopCartItemDto> shopCartItemDtoList;
}
