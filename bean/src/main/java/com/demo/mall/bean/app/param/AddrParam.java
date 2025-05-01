/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "地址參數")
public class AddrParam {

    @Schema(description = "地址ID")
    private Long addrId;

    @NotNull(message = "收件人不能為空")
    @Schema(description = "收件人")
    private String receiver;

    @NotNull(message = "地址不能為空")
    @Schema(description = "地址")
    private String addr;

    @Schema(description = "郵遞區號")
    private String postCode;

    @NotNull(message = "手機不能為空")
    @Schema(description = "手機")
    private String mobile;

    @NotNull(message = "省ID不能為空")
    @Schema(description = "省ID")
    private Long provinceId;

    @NotNull(message = "城市ID不能為空")
    @Schema(description = "城市ID")
    private Long cityId;

    @NotNull(message = "區ID不能為空")
    @Schema(description = "區ID")
    private Long areaId;

    @NotNull(message = "省不能為空")
    @Schema(description = "省")
    private String province;

    @NotNull(message = "城市不能為空")
    @Schema(description = "城市")
    private String city;

    @NotNull(message = "區不能為空")
    @Schema(description = "區")
    private String area;

}
