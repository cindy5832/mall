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

@Data
@Schema(description = "購物車合計")
public class ShopCartAmountDto {

    @Schema(description = "總額")
    private Double totalMoney;

    @Schema(description = "總計")
    private Double finalMoney;

    @Schema(description = "減額")
    private Double subtractMoney;

    @Schema(description = "商品數量")
    private Integer count;
}
