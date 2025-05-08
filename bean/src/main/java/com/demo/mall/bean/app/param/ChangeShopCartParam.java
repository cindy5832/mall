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

@Data
public class ChangeShopCartParam {

    @Schema(description = "購物車ID")
    private Long basketId;

    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID")
    private Long prodId;

    @NotNull(message = "skuId不能為空")
    @Schema(description = "skuId")
    private Long skuId;

    @NotNull(message = "商店ID不能為空")
    @Schema(description = "商店ID")
    private Long shopId;

    @NotNull(message = "商品個數不能為空")
    @Schema(description = "商品個數")
    private Integer count;

    @Schema(description = "推薦人卡號")
    private String distributionCardNo;
}
