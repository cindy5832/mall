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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lanhai
 */
@Data
@TableName("tz_order_refund")
@Schema(description = "訂單退款紀錄")
public class OrderRefund implements Serializable {

    @TableId
    @Schema(description = "退款紀錄id")
    private Long refundId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "訂單id")
    private Long orderId;

    @Schema(description = "訂單流水號")
    private String orderNumber;

    @Schema(description = "訂單總額")
    private Double orderAmount;

    @Schema(description = "訂單項ID 全部退款為0")
    private Long orderItemId;

    @Schema(description = "退款編號")
    private String refundSn;

    @Schema(description = "訂單支付流水號")
    private String flowTradeNo;

    @Schema(description = "第三方退款單號")
    private String outRefundNo;

    @Schema(description = "訂單付款方式 1 微信 2 支付寶")
    private Integer payType;

    @Schema(description = "訂單支付名稱")
    private String payTypeName;

    @Schema(description = "買家id")
    private String userId;

    @Schema(description = "退貨數量")
    private Integer goodsNum;

    @Schema(description = "退款金額")
    private Double refundAmount;

    @Schema(description = "申請類別 1 僅退款 2 退款且退貨")
    private Integer applyType;

    @Schema(description = "處理狀態 1 待審核 2 同意 3 不同意")
    private Integer refundSts;

    @Schema(description = "處理退款狀態 0 退款處理中 1 退款成功 -1 退款失敗")
    private Integer returnMoneySts;

    @Schema(description = "申請時間")
    private Date applyTime;

    @Schema(description = "賣家處理時間")
    private Date handelTime;

    @Schema(description = "退款時間")
    private Date refundTime;

    @Schema(description = "文件憑據json")
    private String photoFiles;

    @Schema(description = "申請原因")
    private String buyerMsg;

    @Schema(description = "賣家備註")
    private String sellerMsg;

    @Schema(description = "物流公司名稱")
    private String expressName;

    @Schema(description = "物流單號")
    private String expressNo;

    @Schema(description = "出貨時間")
    private Date shipTime;

    @Schema(description = "收貨時間")
    private Date receiveTime;

    @Schema(description = "收貨備註")
    private String receiveMessage;

    @Schema(description = "拒絕原因")
    private String rejectMessage;

    @Schema(description = "訂單列表")
    @TableField(exist = false)
    private List<OrderItem> orderItems;
}