package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.dto.HotSearchDto;
import com.demo.mall.bean.model.HotSearch;
import com.demo.mall.dao.HotSearchMapper;
import com.demo.mall.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotSearchServiceImpl extends ServiceImpl<HotSearchMapper, HotSearch> implements HotSearchService {

    @Autowired
    private HotSearchMapper hotSearchMapper;

    @Override
    @CacheEvict(cacheNames = "HotSearchDto", key = "#shopId")
    public void removeHotSearchDtoCatchByShopId(Long shopId) {
    }

    @Override
    public List<HotSearchDto> getHotSearchDtoByShopId(Long shopId) {
        return hotSearchMapper.getHostSearchDtoByShopId(shopId);
    }
}
