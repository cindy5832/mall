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
@TableName("tz_transfee")
public class Transfee implements Serializable {
    @Serial
    private static final long serialVersionUID = 8039640964056626028L;

    @TableId
    @Schema(description = "運費項id")
    private Long transfeeId;

    @Schema(description = "運費模板id")
    private Long transportId;

    @Schema(description = "續件数量")
    private Double continuousPiece;

    @Schema(description = "首件數量")
    private Double firstPiece;

    @Schema(description = "續件費用")
    private Double continuousFee;

    @Schema(description = "首件費用")
    private Double firstFee;

    @TableField(exist=false)
    @Schema(description = "指定條件運費城市項")
    private List<Area> cityList;

}
