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

/**
 * @author lanhai
 */
@Data
@TableName("tz_brand")
@Schema(description = "品牌表")
public class Brand implements Serializable {

    @TableId
    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "品牌名稱")
    private String brandName;

    @Schema(description = "圖片路徑")
    private String brandPic;

    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "備註")
    private String memo;

    @Schema(description = "順序")
    private Integer seq;

    @Schema(description = "狀態 默認1 表示正常狀態 0為下線")
    private Integer status;

    @Schema(description = "商品描述")
    private String brief;

    @Schema(description = "紀錄時間")
    private Date recTime;

    @Schema(description = "更新時間")
    private Date updateTime;

    @Schema(description = "品牌首字母")
    private String firstChar;

    @Schema(description = "內容")
    private String content;
}