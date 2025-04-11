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
@TableName("tz_delivery")
@Schema(description = "物流公司")
public class Delivery implements Serializable {

    @TableId
    @Schema(description = "物流公司id")
    private Long dvyId;

    @Schema(description = "物流公司名稱")
    private String dvyName;

    @Schema(description = "公司主頁")
    private String companyHomeUrl;

    @Schema(description = "建立時間")
    private Date recTime;

    @Schema(description = "修改時間")
    private Date modifyTime;

    @Schema(description = "物流查詢url")
    private String queryUrl;
}