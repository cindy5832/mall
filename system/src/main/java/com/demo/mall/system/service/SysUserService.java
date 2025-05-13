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

    // 根據用戶id獲取用戶訊息
    SysUser getSysUserById(Long userId);

    // 修改密碼
    void updatePasswordByUserId(Long userId, String newPassword);

    // 保存用戶與用戶角色關係
    void saveUserAndUserRole(SysUser sysUser);

    // 更新用戶與用戶關係
    void updateUserAndUserRole(SysUser user);

    // 根據用戶id 批量刪除用戶
    void deleteBatch(Long[] userIds, Long shopId);
}
