package com.demo.mall.api.controller;

import com.demo.mall.bean.app.param.SendSmsParam;
import com.demo.mall.bean.enums.SmsType;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.SmsLogService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/p/sms")
public class SmsController {
    @Autowired
    private SmsLogService smsLogService;

    @PostMapping("/send")
    @Operation(summary = "sms-send", description = "發送驗證碼")
    public ServerResponseEntity audit(@Valid @RequestBody SendSmsParam sendSmsParam) {
        String userId = SecurityUtils.getUser().getUserId();
        smsLogService.sendSms(SmsType.VALID, userId, sendSmsParam.getMobile(), Maps.newHashMap());
        return ServerResponseEntity.success();
    }
}
