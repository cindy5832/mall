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

// 單個商店的訂單訊息
@Data
public class ShopCartOrderDto implements Serializable{

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商店名稱")
    private String shopName;

    @Schema(description = "實際總額")
    private Double actualTotal;

    @Schema(description = "商品總額")
    private Double total;

    @Schema(description = "商品總數")
    private Integer totalCount;

    @Schema(description = "運費")
    private Double transfee;

    @Schema(description = "促銷活動優惠金額")
    private Double discountReduce;

    @Schema(description = "優惠券優惠金額")
    private Double couponReduce;

    @Schema(description = "商店優惠金額 (促銷活動+優惠券+其他)")
    private Double shopReduce = 0.0;

    @Schema(description = "訂單備註訊息")
    private String remarks;

    @Schema(description = "購物車商品")
    private List<ShopCartItemDiscountDto> shopCartItemDiscounts;

    @Schema(description = "整個商店可使用的優惠券列表")
    private List<CouponOrderDto> coupons;

    @Schema(description = "訂單編號")
    private String orderNumber;
}
