package com.demo.mall.security.admin.util;

import com.demo.mall.security.admin.model.ShopUser;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.util.AuthUserContext;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {

    // 獲取用戶
    public ShopUser getSysUser() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        ShopUser details = new ShopUser();
        details.setUserId(Long.valueOf(userInfoInTokenBO.getUserId()));
        details.setEnabled(userInfoInTokenBO.getEnabled());
        details.setUserName(userInfoInTokenBO.getNickName());
        details.setAuthorities(userInfoInTokenBO.getPerms());
        details.setShopId(userInfoInTokenBO.getShopId());
        return details;
    }
}
