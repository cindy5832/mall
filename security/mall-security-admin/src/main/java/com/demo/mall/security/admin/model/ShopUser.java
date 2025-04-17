package com.demo.mall.security.admin.model;

import lombok.Data;

import java.util.Set;

// 用戶詳細訊息
@Data
public class ShopUser {

    private Long userId;

    private boolean enabled;

    private Set<String> authorities;

    private String userName;

    private Long shopId;

}
