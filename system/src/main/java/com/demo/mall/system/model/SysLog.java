/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Schema(description = "系統日誌")
@Data
@TableName("tz_sys_log")
public class SysLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @Schema(description = "用戶名")
    private String username;

    @Schema(description = "操作")
    private String operation;

    @Schema(description = "請求方法")
    private String method;

    @Schema(description = "請求參數")
    private String params;

    @Schema(description = "執行時長(毫秒)")
    private Long time;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "創建時間")
    private Date createDate;

}
