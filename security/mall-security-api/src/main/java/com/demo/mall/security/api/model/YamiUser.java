/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.security.api.model;

import lombok.Data;

// 用戶詳細訊息
@Data
public class YamiUser {

    private String userId;

    private String bizUserId;

    private Boolean enabled;

    // 自取點id
    private Long stationId;

    // 商店id
    private Long shopId;
}
