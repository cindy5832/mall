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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("tz_prod_prop")
@Schema(description = "商品屬性")
public class ProdProp implements Serializable {
    private static final long serialVersionUID = -8761177918672000191L;

    @TableId
    @Schema(description = "商品屬性id")
    private Long propId;

    @Schema(description = "屬性名稱")
    @NotBlank(message = "屬性名稱不得為空")
    private String propName;

    @Schema(description = "商品屬性規則 1 銷售屬性 (規格) 2 參數屬性")
    private Integer rule;

    @Schema(description = "商店id")
    private Long shopId;

    /**
     * 属性值
     */
    @TableField(exist=false)
    @NotEmpty(message="規格屬性值不能為空")
    private List<ProdPropValue> prodPropValues;

}
