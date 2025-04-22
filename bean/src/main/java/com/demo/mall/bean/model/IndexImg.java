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
import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_index_img")
@Schema(description = "主頁輪播圖")
public class IndexImg implements Serializable {
    @Serial
    private static final long serialVersionUID = -3468251351681518798L;

    @TableId
    @Schema(description = "輪播圖片id")
    private Long imgId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "圖片url")
    private String imgUrl;

    @Schema(description = "描述")
    private String des;

    @Schema(description = "標題")
    private String title;

    @Schema(description = "連結")
    private String link;

    @Schema(description = "狀態")
    private Integer status;

    @Schema(description = "順序")
    private Integer seq;

    @Schema(description = "上傳時間")
    private Date uploadTime;

    @Schema(description = "類型")
    private int type;

    @Schema(description = "關聯id")
    private Long relation;

    @TableField(exist = false)
    @JsonSerialize(using = ImgJsonSerializer.class)
    @Schema(description = "商品圖片")
    private String pic;

    @Schema(description = "商品名稱")
    @TableField(exist = false)
    private String prodName;

}
