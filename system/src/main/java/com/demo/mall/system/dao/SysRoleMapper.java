package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.system.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    // 批量刪除
    void deleteBatch(Long[] ids);

    // 根據用戶id獲取角色切換
    List<Long> listRoleIdByUserId(Long userId);
}
