
package com.demo.mall.admin.config;

import cn.hutool.core.lang.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lanhai
 */
@Configuration
@AllArgsConstructor
public class AdminBeanConfig {

    private final AdminConfig adminConfig;

    /**
     * 基於Twitter的Snowflake算法，實現分布式唯一ID
     * 1. 長度為64 bit 的 long
     * 2. 包含 timestamp, machineId, 序列號等
     * 3. 在高併發場景下，能高效的產生唯一ID
     * 4. 避免分庫分表時產生主鍵衝突
     **/
    @Bean
    public Snowflake snowflake() {
        return new Snowflake(adminConfig.getWorkerId(), adminConfig.getDatacenterId());
    }
}
