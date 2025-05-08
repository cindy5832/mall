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

import com.demo.mall.bean.model.Transport;
import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class ProductDto {

    @Schema(description = "商店ID")
    private Long shopId;

    @Schema(description = "商店名稱")
    private String shopName;

    @Schema(description = "商品ID")
    private Long prodId;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "商品價格")
    private Double price;

    @Schema(description = "詳細描述")
    private String content;

    @Schema(description = "商品原價")
    private Double oriPrice;

    @Schema(description = "庫存量")
    private Integer totalStocks;

    @Schema(description = "簡要描述，賣點等")
    private String brief;

    @JsonSerialize(using = ImgJsonSerializer.class)
    @Schema(description = "商品主圖")
    private String pic;

    @JsonSerialize(using = ImgJsonSerializer.class)
    @Schema(description = "商品圖片列表，以逗號分割")
    private String imgs;

    @Schema(description = "商品分類id")
    private Long categoryId;

    @Schema(description = "sku列表")
    private List<SkuDto> skuList;

    @Schema(description = "運費訊息")
    private Transport transport;

    public static interface WithNoContent {
    }

    public static interface WithContent extends WithNoContent {
    }


}
