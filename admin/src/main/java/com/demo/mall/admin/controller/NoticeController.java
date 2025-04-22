package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.Notice;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/shop/notice")
@Tag(name = "shop-notice", description = "商店公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    @Operation(summary = "shop-notice-page", description = "商店公告分頁查詢")
    public ServerResponseEntity<IPage<Notice>> getNoticePage(PageParam<Notice> page, Notice notice) {
        IPage<Notice> iPage = noticeService.page(page, new LambdaQueryWrapper<Notice>()
                .eq(notice.getStatus() != null, Notice::getStatus, notice.getStatus())
                .eq(notice.getIsTop() != null, Notice::getIsTop, notice.getIsTop())
                .like(notice.getTitle() != null, Notice::getTitle, notice.getTitle())
                .orderByDesc(Notice::getUpdateTime)
        );
        return ServerResponseEntity.success(iPage);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "shop-notice-info", description = "通過id查詢公告訊息")
    public ServerResponseEntity<Notice> getInfo(@PathVariable Long id) {
        return ServerResponseEntity.success(noticeService.getById(id));
    }

    @SysOperationLog("新增公告")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('shop:notice:save')")
    @Operation(summary = "shop-notice-save", description = "新增公告")
    public ServerResponseEntity<Boolean> save(@RequestBody @Valid Notice notice) {
        notice.setShopId(SecurityUtils.getSysUser().getShopId());
        if (notice.getStatus() == 1) {
            notice.setPublishTime(new Date());
        }
        notice.setUpdateTime(new Date());
        noticeService.removeNoticeList();
        return ServerResponseEntity.success(noticeService.save(notice));
    }

    @SysOperationLog("修改公告內容")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('shop:notice:update')")
    @Operation(summary = "shop-notice-update", description = "修改公告內容")
    public ServerResponseEntity<Boolean> updateById(@RequestBody @Valid Notice notice) {
        Notice oldNotice = noticeService.getById(notice.getId());
        if (oldNotice.getStatus() == 0 && notice.getStatus() == 1) {
            notice.setPublishTime(new Date());
        }
        notice.setUpdateTime(new Date());
        noticeService.removeNoticeList();
        noticeService.removeNoticeById(notice.getId());
        return ServerResponseEntity.success(noticeService.updateById(notice));
    }

    @SysOperationLog("刪除公告")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('shop:notice:delete')")
    @Operation(summary = "shop-notice-delete", description = "刪除公告")
    public ServerResponseEntity<Boolean> removeById(@PathVariable Long id) {
        noticeService.removeNoticeList();
        noticeService.removeNoticeById(id);
        return ServerResponseEntity.success(noticeService.removeById(id));
    }
}
