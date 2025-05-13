package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.demo.mall.system.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper extends Mapper<SysRoleMenu> {

    // 根據菜單id 刪除菜單關聯角色訊息
    void deleteByMenuId(Long menuId);

    // 根據角色id 批量添加角色與菜單關聯
    void insertRoleAndRoleMenu(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);

    // 根據角色id 批量刪除
    void deleteBatch(Long[] longs);
}
