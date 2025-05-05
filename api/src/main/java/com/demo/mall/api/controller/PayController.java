package com.demo.mall.api.controller;

import com.demo.mall.bean.param.PayParam;
import com.demo.mall.bean.pay.PayInfoDto;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.api.model.YamiUser;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/p/order")
@Tag(name = "order-pay", description = "訂單支付")
public class PayController {
    @Autowired
    private PayService payService;

    @PostMapping("/pay")
    @Operation(summary = "order-pay", description = "支付api-根據訂單號進行支付")
    public ServerResponseEntity pay(@RequestBody PayParam payParam) {
        YamiUser user = SecurityUtils.getUser();
        String userId = user.getUserId();

        PayInfoDto payInfo = payService.pay(userId, payParam);
        payService.paySuccess(payInfo.getPayNo(), "");
        return ServerResponseEntity.success();
    }

    @PostMapping("/normalPay")
    @Operation(summary = "order-normal-pay", description = "一般支付 - 根據單號進行支付")
    public ServerResponseEntity<Boolean> normalPay(@RequestBody PayParam payParam) {
        YamiUser user = SecurityUtils.getUser();
        String userId = user.getUserId();
        PayInfoDto payInfo = payService.pay(userId, payParam);
        payService.paySuccess(payInfo.getPayNo(), "");
        return ServerResponseEntity.success(true);
    }
}
