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
import java.util.Date;

@Data
@TableName("tz_basket")
@Schema(description = "購物車")
public class Basket implements Serializable {

    @TableId
    private Long basketId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "SKU id")
    private Long skuId;

    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "購物車商品數量")
    private Integer basketCount;

    @Schema(description = "購物時間")
    private Date basketDate;

    @Schema(description = "優惠活動id")
    private Long discountId;

    @Schema(description = "推薦人卡號")
    private String distributionCardNo;
}