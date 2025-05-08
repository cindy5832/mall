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

import java.util.List;

@Data
public class TagProductDto {

    @Schema(description = "分組標籤id" )
    private Long id;

    @Schema(description = "分組標籤標題" )
    private String title;

    @Schema(description = "排序（數值越高越靠前）" )
    private String seq;

    @Schema(description = "列表樣式(0:一列一個,1:一列2個,2:一列3個)" )
    private String style;

    private List<ProductDto> productDtoList;
}
