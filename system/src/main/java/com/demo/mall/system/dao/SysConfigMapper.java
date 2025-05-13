package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.system.model.SysConfig;
import org.apache.ibatis.annotations.Param;

public interface SysConfigMapper extends BaseMapper<SysConfig> {

    // 批量刪除系統配置
    void deleteBatch(@Param("ids") Long[] ids);
}
