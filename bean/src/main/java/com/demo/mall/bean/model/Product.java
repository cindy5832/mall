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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("tz_prod")
@Schema(description = "商品")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -4644407386444894349L;

    @TableId
    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "原價")
    private Double oriPrice;

    @Schema(description = "現價")
    private Double price;

    @Schema(description = "簡要描述")
    private String brief;

    // TODO
    @JsonSerialize
    @Schema(description = "商品主圖")
    private String pic;

    @Schema(description = "商品圖片 以 , 隔開")
    private String imgs;

    @Schema(description = "默認1 正常狀態 -1 刪除 0 下架")
    private Integer status;

    @Schema(description = "商品分類id")
    private Long categoryId;

    @Schema(description = "已銷售數量")
    private Integer soldNum;

    @Schema(description = "庫存量")
    private Integer totalStocks;

    /**
     * 配送方式json
     */
    @Schema(description = "配送方式json")
    private String deliveryMode;

    @Schema(description = "運送模板id")
    private Long deliveryTemplateId;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "修改時間")
    private Date updateTime;

    @Schema(description = "詳細描述")
    private String content;

    @Schema(description = "上架時間")
    private Date putawayTime;

    @Version
    @Schema(description = "版本 樂觀鎖")
    private Integer version;

    /**
     * sku列表
     */
    @TableField(exist = false)
    @Schema(description = "sku列表")
    private List<Sku> skuList;

    @TableField(exist = false)
    @Schema(description = "商店名稱")
    private String shopName;

    @TableField(exist = false)
    private List<Long> tagList;


    @Data
    public static class DeliveryModeVO {

        @Schema(description = "用戶自提")
        private Boolean hasUserPickUp;

        @Schema(description = "商店配送")
        private Boolean hasShopDelivery;

    }
}
