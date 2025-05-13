package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    // 根據用戶名獲取一個用戶訊息
    User selectOneByUserName(@Param("userName") String mobileOrUserName);
}
