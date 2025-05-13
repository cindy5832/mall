package com.demo.mall.security.common.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.demo.mall.common.constants.OauthCacheNames;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ResponseEnum;
import com.demo.mall.security.common.bo.TokenInfoBO;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.enums.SysTypeEnum;
import com.demo.mall.security.common.vo.TokenInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenStore {
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenStore(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @return
     * @description: 以Sa-token技術生成token 並返回token訊息
     * @param: userInfoToken
     **/
    public TokenInfoBO storeAccessSaToken(UserInfoInTokenBO userInfoInToken) {
        // 生成token
        int timeOutSecond = getExpiresIn(userInfoInToken.getSysType());
        String uid = this.getUid(userInfoInToken.getSysType().toString(), userInfoInToken.getUserId());
        StpUtil.login(uid, timeOutSecond);
        String token = StpUtil.getTokenValue();
        // 用戶訊息存入緩存
        String keyName = OauthCacheNames.USER_INFO + token;
        redisTemplate.delete(keyName);
        redisTemplate.opsForValue().set(
                keyName,
                JSON.toJSONString(userInfoInToken),
                timeOutSecond,
                TimeUnit.SECONDS
        );
        // 數據封裝返回 (token不加密)
        TokenInfoBO tokenInfoBO = new TokenInfoBO();
        tokenInfoBO.setUserInfoInToken(userInfoInToken);
        tokenInfoBO.setExpiresIn(timeOutSecond);
        tokenInfoBO.setAccessToken(token);
        tokenInfoBO.setRefreshToken(token);
        return tokenInfoBO;
    }

    /**
     * @return java.lang.String
     * @description: 生成名系統唯一uid
     * @param: [sysType, userId]
     **/
    private String getUid(String sysType, String userId) {
        return sysType + ":" + userId;
    }

    /**
     * @return int
     * @description: 計算過期時間 (second)
     * @param: [sysType]
     **/
    private int getExpiresIn(int sysType) {
        int expiresIn = 3600;

        // 一般用戶token過期時間
        if (Objects.equals(sysType, SysTypeEnum.ORDINARY.value())) {
            expiresIn = expiresIn * 24 * 30;
        }
        // 系統管理員token過期時間
        if (Objects.equals(sysType, SysTypeEnum.ADMIN.value())) {
            expiresIn = expiresIn * 24 * 30;
        }
        return expiresIn;
    }

    /**
     * @return com.demo.mall.security.common.bo.UserInfoInTokenBO
     * @description: 根據accessToken獲取用戶訊息
     * @param: [accessToken, needDecrypt]
     **/
    public UserInfoInTokenBO getUserInfoByAccessToken(String accessToken, boolean needDecrypt) {
        if (StrUtil.isBlank(accessToken)) {
            throw new ShopException(ResponseEnum.UNAUTHORIZED, "accessToken is blank");
        }
        String keyName = OauthCacheNames.USER_INFO + accessToken;
        Object redisCache = redisTemplate.opsForValue().get(keyName);
        if (redisCache == null) {
            throw new ShopException(ResponseEnum.UNAUTHORIZED, "登入過期，請重新登入");
        }
        return JSON.parseObject(redisCache.toString(), UserInfoInTokenBO.class);
    }

    public TokenInfoBO refreshToken(String refreshToken) {
        if (StrUtil.isBlank(refreshToken)) {
            throw new ShopException(ResponseEnum.UNAUTHORIZED, "refreshToken is blank");
        }
        // 刪除舊token
        UserInfoInTokenBO userInfoInTokenBO = getUserInfoByAccessToken(refreshToken, false);
        this.deleteCurrentToken(refreshToken);
        // 保存新的token
        return storeAccessSaToken(userInfoInTokenBO);
    }

    // 刪除指定用戶的全部token
    public void deleteAllToken(String sysType, String userId) {
        // 刪除用戶緩存
        String uid = this.getUid(sysType, userId);
        List<String> tokens = StpUtil.getTokenValueListByLoginId(uid);
        if (!CollectionUtils.isEmpty(tokens)) {
            redisTemplate.delete(
                    tokens.stream().map(
                            token -> OauthCacheNames.USER_INFO + token
                    ).collect(Collectors.toList())
            );
        }
        // 移除token
        StpUtil.logout(userId);
    }

    /**
     * @return com.demo.mall.security.common.vo.TokenInfoVO
     * @description: 生成token，並返回token展示訊息
     * @param: [userInfoInToken]
     **/
    public TokenInfoVO storeAndGetVo(UserInfoInTokenBO userInfoInToken) {
        if (!userInfoInToken.getEnabled()) {
            throw new ShopException("用戶已被禁用，請聯繫客服");
        }
        TokenInfoBO tokenInfoBO = storeAccessSaToken(userInfoInToken);
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setAccessToken(tokenInfoBO.getAccessToken());
        tokenInfoVO.setRefreshToken(tokenInfoBO.getRefreshToken());
        tokenInfoVO.setExpiresIn(tokenInfoBO.getExpiresIn());
        return tokenInfoVO;
    }

    /**
     * @return void
     * @description: 刪除當前token
     * @param: [accessToken]
     **/
    public void deleteCurrentToken(String accessToken) {
        // 刪除用戶緩存
        String keyName = OauthCacheNames.USER_INFO + accessToken;
        redisTemplate.delete(keyName);
        // 移除token
        StpUtil.logoutByTokenValue(accessToken);
    }
}
