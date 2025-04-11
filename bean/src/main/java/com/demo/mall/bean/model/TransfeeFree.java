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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("tz_transfee_free")
public class TransfeeFree implements Serializable {
    @Serial
    private static final long serialVersionUID = -2811714952219888223L;

    @TableId
    @Schema(description = "指定條件包郵項id")
    private Long transfeeFreeId;

    @Schema(description = "運費範本id")
    private Long transportId;

    @Schema(description = "包郵方式 (0 滿x件/ 重量/ 體積包郵 1滿金額包郵 2滿x件/ 重量/ 體積且滿金額包郵)")
    private Integer freeType;

    @Schema(description = "需滿金額")
    private Double amount;

    @Schema(description = "包郵x件/ 重量/ 體積")
    private Double piece;

    @TableField(exist=false)
    @Schema(description = "指定條件包郵城市項")
    private List<Area> freeCityList;
}
