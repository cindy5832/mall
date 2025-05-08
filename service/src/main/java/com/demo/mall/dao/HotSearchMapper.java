package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.dto.HotSearchDto;
import com.demo.mall.bean.model.HotSearch;

import java.util.List;

public interface HotSearchMapper extends BaseMapper<HotSearch> {

    // 根據商店id獲取熱搜列表
    List<HotSearchDto> getHostSearchDtoByShopId(Long shopId);
}
