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

import java.util.Date;

@Data
@TableName("tz_sms_log")
@Schema(description = "短信記錄表")
public class SmsLog {

    @TableId
    private Long id;

    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "手機號碼")
    private String userPhone;

    @Schema(description = "短信內容")
    private String content;

    @Schema(description = "手機驗證碼")
    private String mobileCode;

    @Schema(description = "短信類型  1:註冊  2:驗證")
    private Integer type;

    @Schema(description = "發送時間")
    private Date recDate;

    @Schema(description = "發送短信返回碼")
    private String responseCode;

    @Schema(description = "狀態  1:有效  0：失效")
    private Integer status;

}