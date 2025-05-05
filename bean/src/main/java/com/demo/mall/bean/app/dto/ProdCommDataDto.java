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

@Schema(description = "商品評論數據")
@Data
public class ProdCommDataDto {

    @Schema(description = "好評率" )
    private Double positiveRating;

    @Schema(description = "評論數量" )
    private Integer number;

    @Schema(description = "好評數" )
    private Integer praiseNumber;

    @Schema(description = "中評數" )
    private Integer secondaryNumber;

    @Schema(description = "差評數" )
    private Integer negativeNumber;

    @Schema(description = "有圖數" )
    private Integer picNumber;

}
