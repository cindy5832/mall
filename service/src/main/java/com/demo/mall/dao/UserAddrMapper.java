package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.UserAddr;
import org.apache.ibatis.annotations.Param;

public interface UserAddrMapper extends BaseMapper<UserAddr> {

    // 根據用戶id獲取默認地址
    UserAddr getDefaultUserAddr(@Param("userId") String userId);

    // 根據用戶id和地址id獲取地址
    UserAddr getUserAddrByUserIdAndAddrId(@Param("userId") String userId, @Param("addrId") Long addrId);

    // 移除用戶默認地址
    void removeDefaultUserAddr(@Param("userId") String userId);

    // 將地址設置為默認地址
    int setDefaultUserAddr(@Param("addrId") Long addrId, @Param("userId") String userId);
}
