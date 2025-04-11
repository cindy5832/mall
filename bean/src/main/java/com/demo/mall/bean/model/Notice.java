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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告管理
 *
 * @author hzm
 * @date 2019-04-18 21:21:40
 */
@Data
@TableName("tz_notice")
@Schema(description = "公告")
public class Notice implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "公告標題")
    private String title;

    @Schema(description = "公告內容")
    private String content;

    @Schema(description = "狀態 1 公布 0 撤回")
    private Integer status;

    @Schema(description = "是否置頂 1 是 0 否")
    private Integer isTop;

    @Schema(description = "發布時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    @Schema(description = "更新時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
