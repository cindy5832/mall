package com.demo.mall.security.common.adapter;

import java.util.List;

// 實現該接口後，修改需要授權登入的路徑、不須授權登入的路徑
public interface AuthConfigAdapter {

    // 也許需要登入才可使用的url
    String MAYBE_AUTH_URI = "/**/ma/**";

    // 需要授權登入才可用的url
    List<String> pathPatterns();

    // 不需要授權登入的路徑
    List<String> excludePathPatterns();
}
