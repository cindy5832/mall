package com.demo.mall.api.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.NoticeDto;
import com.demo.mall.bean.model.Notice;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/notice")
@Tag(name = "shop-notice", description = "公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/topNoticeList")
    @Operation(summary = "top-notice", description = "獲取所有頂置公告列表訊息")
    public ServerResponseEntity<List<NoticeDto>> getTopNoticeList() {
        List<Notice> noticeList = noticeService.listNotice();
        List<NoticeDto> noticeDtos = BeanUtil.copyToList(noticeList, NoticeDto.class);
        return ServerResponseEntity.success(noticeDtos);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "notice-info", description = "獲取公告詳情")
    public ServerResponseEntity<NoticeDto> getNoticeInfo(@PathVariable("id") Long id) {
        Notice notice = noticeService.getNoticeById(id);
        NoticeDto noticeDto = BeanUtil.copyProperties(notice, NoticeDto.class);
        return ServerResponseEntity.success(noticeDto);
    }

    @GetMapping("/noticeList")
    @Operation(summary = "notice-list", description = "獲取所有公告訊息")
    public ServerResponseEntity<IPage<NoticeDto>> getNoticeList(PageParam<NoticeDto> page) {
        return ServerResponseEntity.success(noticeService.pageNotice(page));
    }
}
