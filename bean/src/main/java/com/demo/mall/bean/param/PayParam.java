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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "支付參數")
public class PayParam {

    @NotBlank(message = "訂單號不能為空")
    @Schema(description = "訂單號")
    private String orderNumbers;

    @NotNull(message = "支付方式不能為空")
    @Schema(description = "支付方式 (1 微信支付 2 支付寶)")
	private Integer payType;

}
