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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lanhai
 */
@Data
@TableName("tz_order")
@Schema(description = "訂單表")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 6222259729062826852L;

    @TableId
    @Schema(description = "訂單id")
    private Long orderId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商品名稱 多個商品以 , 隔開")
    private String prodName;

    @Schema(description = "訂購用戶id")
    private String userId;

    @Schema(description = "流水號")
    private String orderNumber;

    @Schema(description = "總額")
    private Double total;

    @Schema(description = "實際總額")
    private Double actualTotal;

    @Schema(description = "支付方式 0 貨到付款 1 微信 2 支付寶")
    private Integer payType;

    @Schema(description = "訂單備註")
    private String remarks;

    @Schema(description = "訂單狀態 -1 已取消 0 待付款 1 待發貨 2 待收貨 3 已完成")
    private Integer status;

    @Schema(description = "配送類型")
    private String dvyType;

    @Schema(description = "配送方式id")
    private Long dvyId;

    @Schema(description = "物流單號")
    private String dvyFlowId;

    @Schema(description = "訂單運費")
    private Double freightAmount;

    @Schema(description = "用戶訂單地址id")
    private Long addrOrderId;

    @Schema(description = "訂單商品總數")
    private Integer productNums;

    @Schema(description = "訂購時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "訂單更新時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(description = "付款時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @Schema(description = "發貨時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dvyTime;

    @Schema(description = "訂單完成時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finallyTime;

    @Schema(description = "訂單取消時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    @Schema(description = "是否已付款 1 已付款 0 未付款")
    private Integer isPayed;

    @Schema(description = "用戶訂單刪除狀態 0 沒有刪除 1 回收處 2 永久刪除")
    private Integer deleteStatus;

    @Schema(description = "退款狀態 0 默認 1 處理中 2 處理完成")
    private Integer refundSts;

    @Schema(description = "優惠總額")
    private Double reduceAmount;

    @Schema(description = "商店名稱")
    @TableField(exist = false)
    private String shopName;

    @TableField(exist = false)
    private List<OrderItem> orderItems;

    @Schema(description = "用戶訂單地址")
    @TableField(exist = false)
    private UserAddrOrder userAddrOrder;
}
