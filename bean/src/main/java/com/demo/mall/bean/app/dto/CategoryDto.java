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

import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDto {

    @Schema(description = "分類id")
    private Long categoryId;

    @Schema(description = "分類父id")
    private Long parentId;

    @Schema(description = "分類名稱")
    private String categoryName;

    @Schema(description = "分類圖片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

}
