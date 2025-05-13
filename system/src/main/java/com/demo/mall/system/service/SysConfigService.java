package com.demo.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.system.model.SysConfig;

public interface SysConfigService extends IService<SysConfig> {

    // 刪除配置訊息
    void deleteBatch(Long[] ids);
}
