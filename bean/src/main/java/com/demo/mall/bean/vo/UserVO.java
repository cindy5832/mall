/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.vo;

import lombok.Data;

@Data
public class UserVO {

    // 用戶id
    private String userId;

    // 用戶暱稱
    private String nickName;

    // 用戶手機
    private String userMobile;

    // 用戶頭像
    private String pic;
}
