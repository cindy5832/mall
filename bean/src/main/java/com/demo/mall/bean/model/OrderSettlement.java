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
@TableName("tz_order_settlement")
@Schema(description = "訂單支付結算紀錄")
public class OrderSettlement implements Serializable {

    @TableId
    @Schema(description = "支付結算單據ID")
    private Long settlementId;

    @Schema(description = "用戶系統內部的訂單號")
    private String payNo;

    @Schema(description = "外部訂單流水號")
    private String bizPayNo;

    @Schema(description = "訂單流水號")
    private String orderNumber;

    @Schema(description = "支付方式 0 貨到付款 1 微信 2 支付寶")
    private Integer payType;

    @Schema(description = "支付金額")
    private Double payAmount;

    @Schema(description = "用戶ID")
    private String userId;

    @Schema(description = "是否結算 0 否 1 是")
    private Integer isClearing;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "結算時間")
    private Date clearingTime;

    @Schema(description = "支付狀態")
    private Integer payStatus;

    @Schema(description = "版本號")
    private Integer version;

}