package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.mall.bean.app.dto.UserCollectionDto;
import com.demo.mall.bean.model.UserCollection;

public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    // 分頁獲取用戶收藏
    IPage<UserCollectionDto> getUserCollectionDtoPageByUserId(Page page, String userId);
}
