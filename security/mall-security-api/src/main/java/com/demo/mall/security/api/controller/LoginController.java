package com.demo.mall.security.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.mall.bean.model.User;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PrincipalUtil;
import com.demo.mall.dao.UserMapper;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.dto.AuthenticationDTO;
import com.demo.mall.security.common.enums.SysTypeEnum;
import com.demo.mall.security.common.manager.PasswordCheckManager;
import com.demo.mall.security.common.manager.PasswordManager;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.security.common.vo.TokenInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "login", description = "登入")
public class LoginController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordCheckManager passwordCheckManager;

    @Autowired
    private PasswordManager passwordManager;

    @PostMapping("/login")
    @Operation(summary = "login", description = "帳號密碼 (用於前端登入) 通過帳號/手機號/用戶名密碼登入，並攜帶用戶的類型")
    public ServerResponseEntity<TokenInfoVO> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String mobileOrUserName = authenticationDTO.getUserName();
        User user = getUser(mobileOrUserName);

        String decryptPassword = passwordManager.decryptPassword(authenticationDTO.getPassWord());

        // 半小時內密碼輸入錯誤10 限制登入30分鐘
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY, authenticationDTO.getUserName(), decryptPassword, user.getLoginPassword());

        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUserId(user.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setEnabled(user.getStatus() == 1);
        // 儲存token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInTokenBO);
        return ServerResponseEntity.success(tokenInfoVO);
    }

    private User getUser(String mobileOrUserName) {
        User user = null;
        if (PrincipalUtil.isMobile(mobileOrUserName)) {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getUserMobile, mobileOrUserName));
        }
        // 如果不是手機驗證登入，找不到手機號碼就找用戶名
        if (user == null) {
            user = userMapper.selectOneByUserName(mobileOrUserName);
        }
        if (user == null) {
            throw new ShopException("帳號或密碼不正確");
        }
        return user;
    }
}
