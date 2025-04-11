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
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_user_addr")
@Schema(description = "用戶配送地址")
@EqualsAndHashCode(callSuper = false)
public class UserAddr implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "用戶地址id")
    private Long addrId;

    @Schema(description = "用戶ID")
    private String userId;

    @Schema(description = "收貨人")
    private String receiver;

    @Schema(description = "省ID")
    private Long provinceId;

    @Schema(description = "省")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "城市ID")
    private Long cityId;

    @Schema(description = "區")
    private String area;

    @Schema(description = "區ID")
    private Long areaId;

    @Schema(description = "郵編")
    private String postCode;

    private String addr;

    @Schema(description = "地址")
    private String mobile;

    @Schema(description = "狀態,1正常，0無效")
    private Integer status;

    @Schema(description = "是否默認地址 1是")
    private Integer commonAddr;

    @Schema(description = "建立時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "版本號")
    private Integer version;

    @Schema(description = "更新時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
