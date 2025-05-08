package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.UserCollectionDto;
import com.demo.mall.bean.model.UserCollection;
import com.demo.mall.common.utils.PageParam;

public interface UserCollectionService extends IService<UserCollection> {
    // 分頁獲取用戶收藏
    IPage<UserCollectionDto> getUserCollectionDtoPageByUserId(Page page, String userId);
}
