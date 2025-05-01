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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductItemDto implements Serializable {

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "商品個數")
    private Integer prodCount;

    @Schema(description = "商品圖片路徑")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @Schema(description = "商品價格")
    private Double price;

    @Schema(description = "商品總金額")
    private Double productTotalAmount;

    @Schema(description = "商品ID")
    private Long prodId;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "規格名稱")
    private String skuName;

    @Schema(description = "basketId")
    private Long basketId;

    @Schema(description = "商品實際金額 = 商品金額 - 分攤的優惠金額")
    private Double actualTotal;

    @Schema(description = "優惠id，0不主動參與活動（用戶沒有主動參與該活動），-1主動不參與活動")
    private Long discountId = 0L;

    @Schema(description = "分攤的優惠金額")
    private Double shareReduce;

    @Schema(description = "參與活動列表")
    private List<DiscountDto> discounts = new ArrayList<>();
}
