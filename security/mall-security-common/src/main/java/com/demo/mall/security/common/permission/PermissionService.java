package com.demo.mall.security.common.permission;

import cn.hutool.core.util.StrUtil;
import com.demo.mall.security.common.util.AuthUserContext;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

// api 權限判斷工具
@Component("pms")
public class PermissionService {
    /**
     * @return boolean
     * @description: 判斷api是否有 xxx:xxx 權限
     * @param: permission
     **/
    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        return AuthUserContext.get().getPerms()
                .stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }
}
