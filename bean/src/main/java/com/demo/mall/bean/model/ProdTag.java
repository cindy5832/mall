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
@TableName("tz_prod_tag")
@Schema(description = "商品分組標籤")
public class ProdTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1991508792679311621L;

    @TableId
    @Schema(description = "分組標籤id")
    private Long id;

    @Schema(description = "分組標籤")
    private String title;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "狀態 1 正常 0 刪除")
    private Integer status;

    @Schema(description = "默認類型 0 商家自訂 1 系統默認")
    private Integer isDefault;

    @Schema(description = "商品數量")
    private Long prodCount;

    @Schema(description = "排序")
    private Integer seq;

    @Schema(description = "列表樣式 0 一列一個 1 一列2個 2 一列3個")
    private Integer style;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "修改時間")
    private Date updateTime;

    @Schema(description = "刪除時間")
    private Date deleteTime;

}
