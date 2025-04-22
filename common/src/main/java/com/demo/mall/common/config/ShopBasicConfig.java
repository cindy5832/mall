package com.demo.mall.common.config;

import com.demo.mall.common.bean.AliDaYu;
import com.demo.mall.common.bean.ImgUpload;
import com.demo.mall.common.bean.Qiniu;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Tag(name = "mall-properties", description = "商城配置文件")
@Data
@Component
@PropertySource("classpath:shop.properties")
@ConfigurationProperties(prefix = "shop")
public class ShopBasicConfig {

    /**
     * 七牛云的配置信息
     */
    private Qiniu qiniu;

    /**
     * 阿里大鱼短信平台
     */
    private AliDaYu aLiDaYu;

    /**
     * 用于加解密token的密钥
     */
    private String tokenAesKey;

    /**
     * 本地文件上传配置
     */
    private ImgUpload imgUpload;
}
