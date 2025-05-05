package com.demo.mall.service;

import com.demo.mall.bean.param.PayParam;
import com.demo.mall.bean.pay.PayInfoDto;

import java.util.List;

public interface PayService {

    // 支付
    PayInfoDto pay(String userId, PayParam payParam);

    // 支付成功
    List<String> paySuccess(String payNo, String s);
}
