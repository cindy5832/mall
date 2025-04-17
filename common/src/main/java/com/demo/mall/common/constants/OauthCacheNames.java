package com.demo.mall.common.constants;

public final class OauthCacheNames {

    private OauthCacheNames() {}

    // oauth 授權相關key
    public static final String OAUTH_PREFIX = "mall_oauth:";

    // token 授權相關key
    public static final String OAUTH_TOKEN_PREFIX = OAUTH_PREFIX + "token:";

    // 保存token 緩存使用key
    public static final String ACCESS = OAUTH_TOKEN_PREFIX + "access:";

    // 刷新token 緩存使用key
    public static final String REFRESH_TO_ACCESS = OAUTH_TOKEN_PREFIX + "refresh_to_access:";

    // 根據uid獲取保存的token key緩存使用的key
    public static final String UID_TO_ACCESS = OAUTH_TOKEN_PREFIX + "uid_to_access:";

    // 保存token的用戶訊息使用的key
    public static final String USER_INFO = OAUTH_TOKEN_PREFIX + "user_info:";
}
