/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.param;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderParam {
    // 商店id
    private Long shopId;

    // 訂單狀態 -1 已取消 0 待付款 1 待發貨 2 待收貨 3 已完成
    private Integer status;

    // 是否已支付 1 以支付 0 未支付
    private Integer isPayed;

    // 訂單流水號
    private String orderNumber;

    // 開始時間
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    // 結束時間
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
