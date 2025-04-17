package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Area;
import com.demo.mall.dao.AreaMapper;
import com.demo.mall.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Cacheable(cacheNames = "area", key = "#pid")
    public List<Area> listByPid(Long pid) {
        return areaMapper.selectList(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId, pid));
    }

    @Override
    @CacheEvict(cacheNames = "area", key = "#pid")
    public void removeAreaCacheByParentId(Long pid) {

    }
}
