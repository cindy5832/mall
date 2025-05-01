package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.IndexImg;
import com.demo.mall.dao.IndexImgMapper;
import com.demo.mall.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    @CacheEvict(cacheNames = "indexImg", key = "'indexImg'")
    public void removeIndexImgCache() {

    }

    @Override
    public void deleteIndexImgByIds(Long[] ids) {
        indexImgMapper.deleteIndexImgByIds(ids);
    }

    @Override
    @Cacheable(cacheNames = "indexImg", key = "'indexImg'")
    public List<IndexImg> listIndexImg() {
        return indexImgMapper.listIndexImg();
    }
}
