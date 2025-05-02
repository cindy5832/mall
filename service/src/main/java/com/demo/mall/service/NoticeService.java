package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.NoticeDto;
import com.demo.mall.bean.model.Notice;
import com.demo.mall.common.utils.PageParam;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    // 刪除公告緩存
    void removeNoticeList();

    // 根據公告id刪除公告緩存
    void removeNoticeById(Long id);

    // 獲取公告列表
    List<Notice> listNotice();

    // 根據id獲取公告內容
    Notice getNoticeById(Long id);

    // 分頁獲取公告
    Page<NoticeDto> pageNotice(PageParam<NoticeDto> page);
}
