package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.UserAddr;

public interface UserAddrService extends IService<UserAddr> {

    // 刪除緩存
    void removeUserAddrByUserId(long addrId, String userId);

    // 根據用戶id和地址id獲取用戶地址
    UserAddr getUserAddrByUserId(Long addrId, String userId);

    // 更新默認地址
    void updateDefaultUserAddr(Long addrId, String userId);
}
