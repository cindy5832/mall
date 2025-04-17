package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Area;

import java.util.List;

public interface AreaService extends IService<Area> {

    List<Area> listByPid(Long pid);

    void removeAreaCacheByParentId(Long pid);
}
