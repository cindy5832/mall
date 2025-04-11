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
import java.util.Date;

@Data
@TableName("tz_prod_tag_reference")
@Schema(description = "分類標籤引用")
public class ProdTagReference implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "分組引用id")
    private Long referenceId;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "標籤id")
    private Long tagId;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "狀態 1 正常 0 刪除")
    private Integer status;

    @Schema(description = "創建時間")
    private Date createTime;

}
