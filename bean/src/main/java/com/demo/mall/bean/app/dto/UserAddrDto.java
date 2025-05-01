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

import java.io.Serializable;

@Data
public class UserAddrDto implements Serializable {
    @Schema(description = "地址id")
    private Long addrId;

    @Schema(description = "收件人")
    private String receiver;

    @Schema(description = "省")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "區")
    private String area;

    @Schema(description = "地址")
    private String addr;

    @Schema(description = "手機")
    private String mobile;

    @Schema(description = "是否默認地址 (1:是 0：否)")
    private Integer commonAddr;

    @Schema(description = "省ID")
    private Long provinceId;

    @Schema(description = "城市ID")
    private Long cityId;

    @Schema(description = "區域ID")
    private Long areaId;
}
