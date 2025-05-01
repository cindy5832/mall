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

@Schema(description = "我的訂單-訂單項")
@Data
public class MyOrderItemDto {

    @Schema(description = "商品圖片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "商品數量")
    private Integer prodCount;

    @Schema(description = "商品價格")
    private Double price;

    @Schema(description = "skuName")
    private String skuName;

}
