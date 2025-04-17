package com.demo.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.system.model.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * @description: 根據用戶名獲取用戶訊息
     * @param: [userName]
     * @return com.demo.mall.system.model.SysUser
     **/
    SysUser getByUserName(String userName);

    List<String> queryAllPerms(Long userId);
}
