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

import java.io.Serializable;

/**
 * @author lanhai
 */
@Data
@TableName("tz_category_brand")
@Schema(description = "品牌類別")
public class CategoryBrand implements Serializable {
    @TableId
    @Schema(description = "品牌類別id")
    private Long id;

    @Schema(description = "商品類別id")
    private Long categoryId;

    @Schema(description = "品牌id")
    private Long brandId;
}