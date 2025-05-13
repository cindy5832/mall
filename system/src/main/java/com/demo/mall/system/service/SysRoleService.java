package com.demo.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.system.model.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    // 保存角色 與 角色菜單關聯
    void saveRoleAndRoleMenu(SysRole role);

    // 更新角色 與 角色菜單 關聯
    void updateRoleAndRoleMenu(SysRole role);

    // 根據id批量刪除
    void deleteBatch(Long[] ids);

    // 根據用戶id獲取角色id列表
    List<Long> listRoleIdByUserId(Long userId);
}
