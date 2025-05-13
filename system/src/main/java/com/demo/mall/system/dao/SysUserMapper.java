package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.system.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * @description: 根據用戶名獲取管理員用戶
     * @param: [userName]
     * @return com.demo.mall.system.model.SysUser
     **/
    SysUser selectByUsername(@Param("username") String userName);

    /**
     * @description: 查詢用戶的所有權限
     * @param: [userId]
     * @return java.util.List<java.lang.String>
     **/
    List<String> queryAllPerms(@Param("userId") Long userId);

    // 根據用戶id 批量刪除用戶
    void deletBatch(@Param("userIds") Long[] userIds, @Param("shopId") Long shopId);
}
