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

import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class SkuDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6457261945829470666L;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "價格")
    private Double price;

    @Schema(description = "原價")
    private Double oriPrice;

    @Schema(description = "庫存 (-1表示無限)")
    private Integer stocks;

    @Schema(description = "sku名稱")
    private String skuName;

    @Schema(description = "圖片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @Schema(description = "銷售屬性組合字符串，格式是p1:v1;p2:v2")
    private String properties;
}
