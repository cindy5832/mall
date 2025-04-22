package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Notice;
import com.demo.mall.dao.NoticeMapper;
import com.demo.mall.service.NoticeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Override
    @CacheEvict(cacheNames = "notices", key = "'notices'")
    public void removeNoticeList() {

    }

    @Override
    @CacheEvict(cacheNames = "notice", key = "#noticeId")
    public void removeNoticeById(Long noticeId) {

    }
}
