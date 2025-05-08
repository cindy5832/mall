/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "收藏對象")
@Data
public class UserCollectionDto {

    @Schema(description = "收藏id")
    private Long id;

    @Schema(description = "商品名稱")
    private String prodName;

    @Schema(description = "收藏時間")
    private Date createTime;

}
