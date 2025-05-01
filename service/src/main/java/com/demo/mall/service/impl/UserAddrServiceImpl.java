package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.dao.UserAddrMapper;
import com.demo.mall.service.UserAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    @CacheEvict(cacheNames = "UserAddrDto", key = "#userId+':'+#addrId")
    public void removeUserAddrByUserId(long addrId, String userId) {

    }

    @Override
    @Cacheable(cacheNames = "UserAddrDto", key = "#userId+':'+#addrId")
    public UserAddr getUserAddrByUserId(Long addrId, String userId) {
        if(addrId == 0){
            return userAddrMapper.getDefaultUserAddr(userId);
        }
        return userAddrMapper.getUserAddrByUserIdAndAddrId(userId, addrId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDefaultUserAddr(Long addrId, String userId) {
        userAddrMapper.removeDefaultUserAddr(userId);
        int setCount = userAddrMapper.setDefaultUserAddr(addrId, userId);
        if (setCount == 0) {
            throw new ShopException("無法修改用戶默認地址，請稍後再試");
        }
    }
}
