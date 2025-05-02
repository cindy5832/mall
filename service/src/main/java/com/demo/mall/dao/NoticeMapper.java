package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.mall.bean.app.dto.NoticeDto;
import com.demo.mall.bean.model.Notice;
import com.demo.mall.common.utils.PageParam;

public interface NoticeMapper extends BaseMapper<Notice> {

    Page<NoticeDto> pageNotice(PageParam<NoticeDto> page);
}
