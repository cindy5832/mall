package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.SmsLog;
import org.apache.ibatis.annotations.Param;

public interface SmsLogMapper extends BaseMapper<SmsLog> {

    // 根據手機號碼和簡訊類型 讓簡訊失效
    void invalidSmsByMobileAndType(@Param("mobile") String mobile, @Param("type") Integer type);
}
