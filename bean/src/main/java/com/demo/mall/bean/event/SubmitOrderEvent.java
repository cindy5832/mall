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


import com.demo.mall.bean.app.dto.ShopCartOrderMergerDto;
import com.demo.mall.bean.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// 提交訂單事件
@Data
@AllArgsConstructor
public class SubmitOrderEvent {
    /**
     * 完整的订单信息
     */
    private final ShopCartOrderMergerDto mergerOrder;

    private List<Order> orders;

}
