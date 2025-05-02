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
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LGH
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShopCartItemDto extends ProductItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8284981156242930909L;

    @Schema(description = "購物車ID")
    private Long basketId;

    @Schema(description = "商店ID")
    private Long shopId;

    @Schema(description = "規格名稱")
    private String skuName;

    @Schema(description = "商店名稱")
    private String shopName;

    @Schema(description = "商品原價")
    private Double oriPrice;

    @Schema(description = "推廣員使用的卡號")
    private String distributionCardNo;

    @Schema(description = "加入購物車的時間")
    private Date basketDate;
}
