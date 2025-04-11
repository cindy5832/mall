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

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("tz_prod_prop_value")
@Schema(description = "商品屬性值")
public class ProdPropValue implements Serializable {

    @Serial
    private static final long serialVersionUID = 6604406039354172708L;

    @TableId
    @Schema(description = "屬性值ID")
    private Long valueId;

    @Schema(description = "屬性值名稱")
    private String propValue;

    @Schema(description = "屬性id")
    private Long propId;

}