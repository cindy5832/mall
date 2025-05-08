package com.demo.mall.api.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.mall.bean.model.User;
import com.demo.mall.bean.param.UserRegisterParam;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.enums.SysTypeEnum;
import com.demo.mall.security.common.manager.PasswordManager;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.security.common.vo.TokenInfoVO;
import com.demo.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
@Tag(name = "user-register", description = "用戶註冊相關")
public class UserRegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordManager passwordManager;

    @PostMapping("/register")
    @Operation(summary = "register", description = "用戶註冊或綁定手機")
    public ServerResponseEntity<TokenInfoVO> register(@Valid @RequestBody UserRegisterParam userRegisterParam) {
        if (StrUtil.isBlank(userRegisterParam.getNickName())) {
            userRegisterParam.setNickName(userRegisterParam.getUserName());
        }
        // 正在進行申請註冊
        if (userService.count(new QueryWrapper<User>()
                .eq(userRegisterParam.getNickName(), userRegisterParam.getNickName())) > 0) {
            throw new ShopException("該用戶名已註冊，無法重新註冊");
        }
        Date now = new Date();
        User user = new User();
        user.setModifyTime(now);
        user.setUserRegtime(now);
        user.setStatus(1);
        user.setNickName(userRegisterParam.getNickName());
        user.setUserMail(userRegisterParam.getUserMail());
        String decryptPassword = passwordManager.decryptPassword(userRegisterParam.getPassWord());
        user.setLoginPassword(passwordEncoder.encode(decryptPassword));
        String userId = IdUtil.simpleUUID();
        userService.save(user);

        // 登入
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        userInfoInTokenBO.setEnabled(true);
        return ServerResponseEntity.success(tokenStore.storeAndGetVo(userInfoInTokenBO));
    }

    @PutMapping("/updatePwd")
    @Operation(summary = "update-password", description = "修改密碼")
    public ServerResponseEntity updatePwd(@Valid @RequestBody UserRegisterParam userRegisterParam) {
        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getNickName, userRegisterParam.getNickName()));
        if (user == null) {
            // 無法獲取用戶訊息
            throw new ShopException("無法獲取用戶訊息");
        }
        String decryptPassword = passwordManager.decryptPassword(userRegisterParam.getPassWord());
        if (StrUtil.isBlank(decryptPassword)) {
            throw new ShopException("新密碼不能為空");
        }
        String password = passwordEncoder.encode(decryptPassword);
        if (StrUtil.equals(password, user.getLoginPassword())) {
            throw new ShopException("新舊密碼不能相同");
        }
        user.setModifyTime(new Date());
        user.setLoginPassword(password);
        userService.updateById(user);
        return ServerResponseEntity.success();
    }
}
