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


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Schema(description = "系統用戶")
@Data
@TableName("tz_sys_user")
public class SysUser implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "用戶id")
	@TableId
	private Long userId;

	@Schema(description = "用戶名")
	@NotBlank(message="用戶名不能為空")
	@Size(min = 2,max = 20,message = "用戶名長度要在2-20之間")
	private String username;

	@Schema(description = "密碼")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Schema(description = "email")
	@NotBlank(message="email不能為空")
	@Email(message="email格式不正確")
	private String email;

	@Schema(description = "手機號碼")
	@Pattern(regexp="0?1[0-9]{10}",message = "請輸入正確的手機號碼")
	private String mobile;

	@Schema(description = "狀態 0 禁用 1 正常")
	private Integer status;

	@Schema(description = "用戶所在的商店id")
	private Long shopId;

	@Schema(description = "角色id列表")
	@TableField(exist=false)
	private List<Long> roleIdList;

	@Schema(description = "創建時間")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
