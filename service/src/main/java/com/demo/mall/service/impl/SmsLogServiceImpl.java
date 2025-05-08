package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.enums.SmsType;
import com.demo.mall.bean.model.SmsLog;
import com.demo.mall.common.bean.AliDaYu;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.utils.Json;
import com.demo.mall.dao.SmsLogMapper;
import com.demo.mall.service.SmsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class SmsLogServiceImpl extends ServiceImpl<SmsLogMapper, SmsLog> implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Autowired
    private AliDaYu aliDaYu;

    // 產品名稱:雲通信短信API產品
    private static final String PRODUCT = "Dysmsapi";

    // 商品域名
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    // 當天最大驗證碼短信發送量
    private static final int TODAY_MAX_SEND_VALID_SMS_NUMBER = 10;

    // 短信發送成功的標誌
    private static final String SEND_SMS_SUCCESS_FLAG = "OK";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSms(SmsType smsType, String userId, String mobile, Map<String, String> params) {
        SmsLog smsLog = new SmsLog();
        if (smsType.equals(SmsType.VALID)) {
            // 阿里雲規定：使用同一個簽名，默認情逛下對同一個手機號碼發送簡訊，最多支持1條/分鐘，5條/小時，10條/天
            long todaySendSmsNumber = smsLogMapper.selectCount(new LambdaQueryWrapper<SmsLog>()
                    .gt(SmsLog::getRecDate, DateUtil.beginOfDay(new Date()))
                    .lt(SmsLog::getRecDate, DateUtil.endOfDay(new Date()))
                    .eq(SmsLog::getUserId, userId)
                    .eq(SmsLog::getType, smsType.value()));
            if (todaySendSmsNumber > TODAY_MAX_SEND_VALID_SMS_NUMBER) {
                throw new ShopException("今日發送簡訊驗證碼次數已達上限");
            }
            // 將上一條簡訊驗證碼失效
            smsLogMapper.invalidSmsByMobileAndType(mobile, smsType.value());

            String code = RandomUtil.randomNumbers(6);
            params.put("code", code);
        }
        smsLog.setType(smsType.value());
        smsLog.setMobileCode(params.get("code"));
        smsLog.setRecDate(new Date());
        smsLog.setUserId(userId);
        smsLog.setStatus(1);
        smsLog.setUserPhone(mobile);
        smsLog.setContent(formatContent(smsType, params));
        smsLogMapper.insert(smsLog);
        try {
            this.sendSms(mobile, smsType.getTemplateCode(), params);
        } catch (ClientException e) {
            throw new ShopException("簡訊發送失敗，請稍後再試");
        }
    }

    private String formatContent(SmsType smsType, Map<String, String> params) {
        if (CollectionUtil.isEmpty(params)) {
            return smsType.getContent();
        }
        String content = smsType.getContent();
        for (Map.Entry<String, String> element : params.entrySet()) {
            content = content.replace("${" + element.getKey() + "}", element.getValue());
        }
        return content;
    }

    private void sendSms(String mobile, String templateCode, Map<String, String> params) throws ClientException {
        // 自動調整時間超時時間
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient，暫不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliDaYu.getAccessKeyId(), aliDaYu.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 組裝請求對象
        SendSmsRequest request = new SendSmsRequest();
        // 待發送手機號碼 必填
        request.setPhoneNumbers(mobile);
        // 簡訊簽名 必填
        request.setSignName(aliDaYu.getSignName());
        // 簡訊模板 必填
        request.setTemplateCode(templateCode);
        request.setTemplateParam(Json.toJsonString(params));

        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        log.debug(Json.toJsonString(sendSmsResponse));
        if (sendSmsResponse.getCode() == null || !SEND_SMS_SUCCESS_FLAG.equals(sendSmsResponse.getCode())) {
            throw new ShopException("簡訊發送失敗，請稍號再試" + sendSmsResponse.getMessage());
        }
    }
}
