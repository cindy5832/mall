package com.demo.mall.common.config;

import cn.hutool.crypto.symmetric.AES;
import com.demo.mall.common.bean.AliDaYu;
import com.demo.mall.common.bean.ImgUpload;
import com.demo.mall.common.bean.Qiniu;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ShopBeanConfig {
    private final ShopBasicConfig ShopBasicConfig;

    @Bean
    public Qiniu qiniu() {
        return ShopBasicConfig.getQiniu();
    }

    @Bean
    public AES tokenAes() {
        return new AES(ShopBasicConfig.getTokenAesKey().getBytes());
    }

    @Bean
    public AliDaYu aLiDaYu() {
        return ShopBasicConfig.getALiDaYu();
    }

    @Bean
    public ImgUpload imgUpload() {
        return ShopBasicConfig.getImgUpload();
    }
}
