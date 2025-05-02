/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderNumbersDto {

    @Schema(description = "多個訂單號拼接String")
    private String orderNumbers;

    public OrderNumbersDto(String orderNumbers) {
        this.orderNumbers = orderNumbers;
    }

    public OrderNumbersDto() {
    }
}
