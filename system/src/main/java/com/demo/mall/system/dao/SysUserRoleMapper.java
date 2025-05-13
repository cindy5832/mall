package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.system.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    // 根據角色id 批量刪除
    void deleteBatch(@Param("roleIds") Long[] roleIds);

    // 根据用户id批量增加用户角色關係
    void insertUserAndUserRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    // 根據用戶id刪除角色與用戶關係
    void deleteByUserId(Long userId);
}
