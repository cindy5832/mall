/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "訂單參數")
public class OrderParam {


    @Schema(description = "購物車id數組")
    private List<Long> basketIds;

    @Schema(description = "立即購物時提交的商品項目")
    private OrderItemParam orderItem;

    @Schema(description = "地址ID，0為默認地址")
    @NotNull(message = "地址不能為空")
    private Long addrId;

    @Schema(description = "用戶是否改變了優惠券的選擇用户，若改了則根據傳入參數選擇優惠券")
    private Integer userChangeCoupon;

    @Schema(description = "優惠券ID數組")
    private List<Long> couponIds;

}
