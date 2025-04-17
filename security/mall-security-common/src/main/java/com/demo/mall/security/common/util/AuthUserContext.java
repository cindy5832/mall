package com.demo.mall.security.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;

public class AuthUserContext {
    private static final ThreadLocal<UserInfoInTokenBO> USER_INFO_IN_TOKEN_HOLDER = new TransmittableThreadLocal<>();

    public static UserInfoInTokenBO get(){
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(UserInfoInTokenBO userInfoInTokenBO){
        USER_INFO_IN_TOKEN_HOLDER.set(userInfoInTokenBO);
    }

    public static void clean(){
        if(USER_INFO_IN_TOKEN_HOLDER.get() != null){
            USER_INFO_IN_TOKEN_HOLDER.remove();
        }
    }
}
