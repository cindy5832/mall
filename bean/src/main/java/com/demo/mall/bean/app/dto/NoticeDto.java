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

@Schema(description = "公告對象")
@Data
public class NoticeDto {

    @Schema(description = "公告id" )
    private Long id;

    @Schema(description = "商店id" )
    private Long shopId;

    @Schema(description = "標題" )
    private String title;

    @Schema(description = "公告內容" )
    private String content;

    @Schema(description = "公告發布時間" )
    private Date publishTime;

}
