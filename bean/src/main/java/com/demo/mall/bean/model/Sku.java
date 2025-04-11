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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_sku")
@Schema(description = "單品SKU表")
public class Sku implements Serializable {

    @TableId
    @Schema(description = "單品ID")
    private Long skuId;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "銷售屬性組合字串 格式是p1:v1;p2:v2")
    private String properties;

    @Schema(description = "原價")
    private Double oriPrice;

    @Schema(description = "價格")
    private Double price;

    @Schema(description = "商品在付款減庫存的狀態下，該sku上未付款的訂單數量")
    private Integer stocks;

    @Schema(description = "商品在付款減庫存的狀態下，該sku上未付款的訂單數量")
    private Integer actualStocks;

    @Schema(description = "修改時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(description = "記錄時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recTime;

    @Schema(description = "商家編碼")
    private String partyCode;

    @Schema(description = "商品條碼")
    private String modelId;

    @Schema(description = "sku圖片")
    private String pic;

    @Schema(description = "sku名稱")
    private String skuName;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "版本號")
    private Integer version;

    @Schema(description = "商品重量")
    private Double weight;

    @Schema(description = "商品體積")
    private Double volume;

    @Schema(description = "狀態 0 禁用 1 啟用")
    private Integer status;

    @Schema(description = "是否被刪除 0 正常 1 已被刪除")
    private Integer isDelete;

}
