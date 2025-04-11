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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("tz_transport")
@Schema(description = "運費範本")
public class Transport implements Serializable {
    @Serial
    private static final long serialVersionUID = 1876655654053364580L;

    @TableId
    @Schema(description = "運費範本id")
    private Long transportId;

    @Schema(description = "運費範本名稱")
    private String transName;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "商店id")
    private Long shopId;

    /**
     * 參考 TransportChargeType
     */
    @Schema(description = "收費方式（0 按件數,1 按重量 2 按體積）")
    private Integer chargeType;

    @Schema(description = "是否包郵 0:不包郵 1:包郵")
    private Integer isFreeFee;

    @Schema(description = "是否含有包郵條件 0 否 1是")
    private Integer hasFreeCondition;

    @TableField(exist=false)
    @Schema(description = "指定條件包郵項")
    private List<TransfeeFree> transfeeFrees;

    @TableField(exist=false)
    @Schema(description = "運費項")
    private List<Transfee> transfees;

}
