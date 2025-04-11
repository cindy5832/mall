/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lanhai
 */
@Data
@TableName("tz_order_item")
@Schema(description = "訂單項目表")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 7307405761190788407L;

    @Schema(description = "訂單Id")
    @TableId
    private Long orderItemId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "訂單的流水號")
    private String orderNumber;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "商品SKUid")
    private Long skuId;

    @Schema(description = "購物車商品個數")
    private Integer prodCount;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "SKU名稱")
    private String skuName;

    @Schema(description = "商品主圖圖片路徑")
    private String pic;

    @Schema(description = "商品價格")
    private Double price;

    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "商品總金額")
    private Double productTotalAmount;

    @Schema(description = "購物時間")
    private Date recTime;

    @Schema(description = "評論狀態 0 未評價 1 已評價")
    private Integer commSts;

    @Schema(description = "推廣員使用的推銷卡號")
    private String distributionCardNo;

    @Schema(description = "加入購物車時間")
    private Date basketDate;
}
