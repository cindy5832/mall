package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.enums.SmsType;
import com.demo.mall.bean.model.SmsLog;

import java.util.HashMap;
import java.util.Map;

public interface SmsLogService extends IService<SmsLog> {

    // 發送簡訊
    void sendSms(SmsType smsType, String userId, String mobile, Map<String, String> objectObjectHashMap);
}
