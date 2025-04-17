package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.admin.dto.CaptchaAuthenticationDTO;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.enums.SysTypeEnum;
import com.demo.mall.security.common.manager.PasswordCheckManager;
import com.demo.mall.security.common.manager.PasswordManager;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.security.common.vo.TokenInfoVO;
import com.demo.mall.system.constant.Constant;
import com.demo.mall.system.model.SysMenu;
import com.demo.mall.system.model.SysUser;
import com.demo.mall.system.service.SysMenuService;
import com.demo.mall.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Tag(name = "admin-login", description = "管理員登入")
public class AdminLoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private PasswordCheckManager passwordCheckManager;

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private TokenStore tokenStore;


    @PostMapping("/adminLogin")
    @Operation(summary = "帳號密碼 + 驗證碼登入 (用於後台登入)", description = "通過帳號/手機號碼/用戶帳密登入")
    public ServerResponseEntity<?> login(@Valid @RequestBody CaptchaAuthenticationDTO captchaAuthenticationDTO) {
        // 後臺登入需要在校驗一次驗證碼
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaAuthenticationDTO.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        if (!response.isSuccess()) {
            return ServerResponseEntity.showFailMsg("驗證碼錯誤或已過期");
        }
        SysUser sysUser = sysUserService.getByUserName(captchaAuthenticationDTO.getUserName());
        if (sysUser == null) {
            throw new ShopException("帳號或密碼不正確");
        }
        // 半小時內密碼輸入錯誤10次，限制登入30分鐘
        String decryptPassword = passwordManager.decryptPassword(captchaAuthenticationDTO.getPassWord());
        passwordCheckManager.checkPassword(SysTypeEnum.ADMIN, captchaAuthenticationDTO.getUserName(), decryptPassword, sysUser.getPassword());

        // 若非商店的超級管理員且是禁用狀態，無法登入
        if (Objects.equals(sysUser.getStatus(), 0)) {
            // 未找到此用戶訊息
            throw new ShopException("未找到此用戶訊息");
        }

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(String.valueOf(sysUser.getUserId()));
        userInfoInToken.setSysType(SysTypeEnum.ADMIN.value());
        userInfoInToken.setEnabled(sysUser.getStatus() == 1);
        userInfoInToken.setPerms(getUserPermissions(sysUser.getUserId()));
        userInfoInToken.setNickName(sysUser.getUsername());
        userInfoInToken.setShopId(sysUser.getShopId());

        // 儲存在token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ServerResponseEntity.success(tokenInfoVO);
    }

    private Set<String> getUserPermissions(Long userId) {
        List<String> permList;
        // 系統管理員，具有最高權限
        if (userId == Constant.SUPER_ADMIN_ID) {
            List<SysMenu> menuList = sysMenuService.list(Wrappers.emptyWrapper());
            permList = menuList.stream().map(SysMenu::getPerms).toList();
        } else {
            permList = sysUserService.queryAllPerms(userId);
        }
        return permList.stream().flatMap(
                perms -> {
                    if (StrUtil.isBlank(perms)) {
                        return null;
                    }
                    return Arrays.stream(perms.trim().split(StrUtil.COMMA));
                }).collect(Collectors.toSet());
    }
}
