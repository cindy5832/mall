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


import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.dto.ShopCartOrderDto;
import com.demo.mall.bean.app.param.OrderParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// 確認訂單時的事件
@Data
@AllArgsConstructor
public class ConfirmOrderEvent {

    // 購物車已經組裝好的商店訂單訊息
    private ShopCartOrderDto shopCartOrderDto;

    // 下單的請求參數
    private OrderParam orderParam;

    // 商店中的所有商品項
    private List<ShopCartItemDto> shopCartItems;
}
