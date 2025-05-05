/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.pay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "支付訊息")
@Data
public class PayInfoDto {

    // 支付訊息 如商品名稱
    private String body;

    // 支付單號
    private String payNo;

    // 付款金額
    private Double payAmount;
}
