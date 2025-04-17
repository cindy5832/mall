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

// token訊息，此訊息存在redis中
@Data
public class TokenInfoBO {

     // 保存在token訊息裡的用戶訊息
    private UserInfoInTokenBO userInfoInToken;

    private String accessToken;

    private String refreshToken;

    // 幾秒後過期
    private Integer expiresIn;

}
