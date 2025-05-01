package com.demo.mall.security.api.util;

import com.demo.mall.common.utils.HttpContextUtils;
import com.demo.mall.security.api.model.YamiUser;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.util.AuthUserContext;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {
    private static final String USER_REQUEST = "/p/";

    // 獲取用戶
    public YamiUser getUser() {
        if (!HttpContextUtils.getHttpServletRequest().getRequestURI().startsWith(USER_REQUEST)) {
            // 用戶相關聯請求 須以/p開頭
            throw new RuntimeException("user.request.error");
        }
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        YamiUser yamiUser = new YamiUser();
        yamiUser.setUserId(userInfoInTokenBO.getUserId());
        yamiUser.setBizUserId(userInfoInTokenBO.getBizUserId());
        yamiUser.setEnabled(userInfoInTokenBO.getEnabled());
        yamiUser.setShopId(userInfoInTokenBO.getShopId());
        yamiUser.setStationId(yamiUser.getStationId());
        return yamiUser;
    }
}
