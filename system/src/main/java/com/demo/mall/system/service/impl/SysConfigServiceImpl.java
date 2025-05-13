package com.demo.mall.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.system.dao.SysConfigMapper;
import com.demo.mall.system.model.SysConfig;
import com.demo.mall.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public void deleteBatch(Long[] ids) {
        sysConfigMapper.deleteBatch(ids);
    }
}
