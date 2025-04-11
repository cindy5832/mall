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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lanhai
 */
@Data
@TableName("tz_category")
@Schema(description = "商品類別")
public class Category implements Serializable {

    @TableId
    @Schema(description = "類別id")
    private Long categoryId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "父節點")
    private Long parentId = 0L;

    @Schema(description = "商品類別名稱")
    private String categoryName;

    @Schema(description = "類別icon")
    private String icon;

    // TODO
    @JsonSerialize
    @Schema(description = "類別顯示圖片")
    private String pic;

    @Schema(description = "順序")
    private Integer seq;

    @Schema(description = "狀態 默認1開啟 0關閉")
    private Integer status;

    @Schema(description = "姬路時間")
    private Date recTime;

    @Schema(description = "分類層級")
    private Integer grade;

    @Schema(description = "更新時間")
    private Date updateTime;

    @TableField(exist = false)
    @Schema(description = "品牌id")
    private List<Long> brandIds;

    @TableField(exist = false)
    @Schema(description = "參數id")
    private List<Long> attributeIds;

    /**
     * 品牌列表
     */
    @TableField(exist = false)
    @Schema(description = "品牌列表")
    private List<Brand> brands;

    /**
     * 参数列表
     */
    @TableField(exist = false)
    @Schema(description = "參數列表")
    private List<ProdProp> prodProps;

    /**
     * 商品列表
     */
    @TableField(exist = false)
    @Schema(description = "商品列表")
    private List<Product> products;

    @TableField(exist = false)
    private List<Category> categories;
}
