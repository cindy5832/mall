package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.dto.HotSearchDto;
import com.demo.mall.bean.model.HotSearch;

import java.util.List;


public interface HotSearchService extends IService<HotSearch> {

    // 根據商店id刪除熱搜緩存
    void removeHotSearchDtoCatchByShopId(Long shopId);

    // 根據商店id獲取熱搜
    List<HotSearchDto> getHotSearchDtoByShopId(Long shopId);
}
