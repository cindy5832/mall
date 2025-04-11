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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_user_addr_order")
@Schema(description = "使用者訂單配送位址")
public class UserAddrOrder implements Serializable {

    @TableId
    @Schema(description = "使用者訂單地址ID")
    private Long addrOrderId;

    @Schema(description = "地址ID")
    private Long addrId;

    @Schema(description = "用戶ID")
    private String userId;

    @Schema(description = "收貨人")
    private String receiver;

    @Schema(description = "省ID")
    private Long provinceId;

    @Schema(description = "省")
    private String province;

    @Schema(description = "區域ID")
    private Long areaId;

    @Schema(description = "區")
    private String area;

    @Schema(description = "城市ID")
    private Long cityId;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "地址")
    private String addr;

    @Schema(description = "郵編")
    private String postCode;

    @Schema(description = "手機")
    private String mobile;

    @Schema(description = "建立時間")
    private Date createTime;

    @Schema(description = "版本號")
    private Integer version;

}