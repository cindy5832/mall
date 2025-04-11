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

/**
 * @author lanhai
 */
@Data
@TableName("tz_pick_addr")
@Schema(description = "用戶配送地址")
public class PickAddr implements Serializable {

    @TableId
    @Schema(description = "地址id")
    private Long addrId;

    @Schema(description = "自提點名稱")
    private String addrName;

    @Schema(description = "地址")
    private String addr;

    @Schema(description = "手機")
    private String mobile;

    @Schema(description = "省分id")
    private Long provinceId;

    @Schema(description = "省分")
    private String province;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "區/縣ID")
    private Long areaId;

    @Schema(description = "區")
    private String area;

    @Schema(description = "商店id")
    private Long shopId;

}