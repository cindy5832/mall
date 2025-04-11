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

@Data
@TableName("tz_user")
@Schema(description = "用戶表")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2090714647038636896L;

    @TableId(type = IdType.INPUT)
    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "用戶暱稱")
    private String nickName;

    @Schema(description = "真實姓名")
    private String realName;

    @Schema(description = "用戶信箱")
    private String userMail;

    @Schema(description = "登入密碼")
    private String loginPassword;

    @Schema(description = "支付密碼")
    private String payPassword;

    @Schema(description = "手機號碼")
    private String userMobile;

    @Schema(description = "修改時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @Schema(description = "註冊時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userRegtime;

    @Schema(description = "註冊ip")
    private String userRegip;

    @Schema(description = "最後登入時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userLasttime;

    @Schema(description = "最後登入IP")
    private String userLastip;

    @Schema(description = "備註")
    private String userMemo;

    @Schema(description = "M(男) or F(女)")
    private String sex;

    @Schema(description = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @Schema(description = "頭像")
    private String pic;

    @Schema(description = "狀態 0無效 1 正常")
    private Integer status;

    @Schema(description = "積分")
    private Integer score;

}
