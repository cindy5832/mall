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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "購物車物品參數")
public class OrderItemParam {

    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID")
    private Long prodId;

    @NotNull(message = "skuId不能為空")
    @Schema(description = "skuId")
    private Long skuId;

    @NotNull(message = "商品數量不能為空")
    @Min(value = 1, message = "商品數量需>0")
    @Schema(description = "商品數量")
    private Integer prodCount;

    @NotNull(message = "商店ID不能為空")
    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "推廣員使用的卡號")
    private String distributionCardNo;
}
