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
@Schema(description = "我的訂單數量")
public class OrderCountData {

    @Schema(description = "所有訂單數量" )
    private Integer allCount;

    @Schema(description = "待付款" )
    private Integer unPay;

    @Schema(description = "待發貨" )
    private Integer payed;

    @Schema(description = "待收貨" )
    private Integer consignment;

    @Schema(description = "待評價" )
    private Integer confirm;

    @Schema(description = "成功" )
    private Integer success;

    @Schema(description = "失敗" )
    private Integer close;


}
