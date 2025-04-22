package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Notice;

public interface NoticeService extends IService<Notice> {

    // 刪除公告緩存
    void removeNoticeList();

    // 根據公告id刪除公告緩存
    void removeNoticeById(Long id);
}
