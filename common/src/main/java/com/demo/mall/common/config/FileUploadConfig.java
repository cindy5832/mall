/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.common.config;

import com.demo.mall.common.bean.Qiniu;
import com.demo.mall.common.enums.QiniuZone;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Tag(name = "file-upload-config-qiniu", description = "文件上傳配置")
@Configuration
public class FileUploadConfig {


    @Autowired
    private Qiniu qiniu;

    // 根據配置文件選擇機房
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        Zone zone = null;
        if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_BEI)) {
            zone = Zone.huabei();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_DONG)) {
            zone = Zone.huadong();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_NAN)) {
            zone = Zone.huanan();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.BEI_MEI)) {
            zone = Zone.beimei();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.XIN_JIA_PO)) {
            zone = Zone.xinjiapo();
        }
        return new com.qiniu.storage.Configuration(zone);
    }

    // 創驗一個上傳工具實體
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    // 認證訊息實體
    @Bean
    public Auth auth() {
        return Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
    }

    // 創建qiniu bucket
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
}
