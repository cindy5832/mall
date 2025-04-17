/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.security.common.bo;

import lombok.Data;

import java.util.Set;

// 保存在token裡的用戶訊息
@Data
public class UserInfoInTokenBO {

    // 用戶自己系統的用戶id
    private String userId;

    // 商家id
    private Long shopId;

    // 暱稱
    private String nickName;

    // 系統類型 SysTypeEnum
    private Integer sysType;

    // 是否是管理員
    private Integer isAdmin;

    private String bizUserId;

    // 權限列表
    private Set<String> perms;

    // 狀態 1 正常 0 關閉
    private Boolean enabled;

    // 其他id
    private Long otherId;

}
