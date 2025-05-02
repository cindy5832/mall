/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

// 多個商店訂單合併在一起
@Data
public class ShopCartOrderMergerDto implements Serializable{

    @Schema(description = "實際總值")
    private Double actualTotal;

    @Schema(description = "商品總值")
    private Double total;

    @Schema(description = "商品總數")
    private Integer totalCount;

    @Schema(description = "訂單優惠金額 (所有商店優惠金額總額)")
    private Double orderReduce;

    @Schema(description = "地址Dto")
    private UserAddrDto userAddr;

    @Schema(description = "每個商店的購物車訊息")
    private List<ShopCartOrderDto> shopCartOrders;

    @Schema(description = "整個訂單可使用的優惠券列表")
    private List<CouponOrderDto> coupons;
}
