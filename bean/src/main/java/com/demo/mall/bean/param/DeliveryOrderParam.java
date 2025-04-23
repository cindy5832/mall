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
import lombok.Data;

@Data
public class DeliveryOrderParam {

    @NotBlank(message = "訂單號不能為空")
    @Schema(description = "訂單號")
    private String orderNumber;

    @NotBlank(message = "物流公司id")
    @Schema(description = "物流公司")
    private Long dvyId;

    @NotBlank(message = "物流單號不能為空")
    @Schema(description = "物流單號")
    private String dvyFlowId;


}
