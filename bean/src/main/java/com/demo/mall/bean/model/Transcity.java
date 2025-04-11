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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tz_transcity")
public class Transcity implements Serializable {

    @TableId
    private Long transcityId;

    @Schema(description = "運費項id")
    private Long transfeeId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "城市名稱")
    @TableField(exist = false)
    private String areaName;
}