package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.enums.MessageStatus;
import com.demo.mall.bean.model.Message;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/admin/message")
@Tag(name = "admin-message", description = "留言管理")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:message:page')")
    @Operation(summary = "admin-message-page", description = "留言分頁查詢")
    public ServerResponseEntity<IPage<Message>> page(Message message, PageParam<Message> page) {
        IPage<Message> messages = messageService.page(page, new LambdaQueryWrapper<Message>()
                .like(StrUtil.isNotBlank(message.getUserName()), Message::getUserName, message.getUserName())
                .eq(message.getStatus() != null, Message::getStatus, message.getStatus())
        );
        return ServerResponseEntity.success(messages);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:info')")
    @Operation(summary = "admin-message-info", description = "獲得留言資訊")
    public ServerResponseEntity<Message> info(@PathVariable("id") Long id) {
        Message message = messageService.getById(id);
        return ServerResponseEntity.success(message);
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:message:update')")
    @Operation(summary = "admin-messaage-update", description = "修改留言訊息")
    public ServerResponseEntity update(@RequestBody Message message) {
        messageService.updateById(message);
        return ServerResponseEntity.success();
    }

    @PutMapping("/release/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:release')")
    @Operation(summary = "admin-message-release", description = "公開留言")
    public ServerResponseEntity release(@PathVariable("id") Long id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(MessageStatus.RELEASE.value());
        messageService.updateById(message);
        return ServerResponseEntity.success();
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("@pms.hasPermission('admin:message:cancel')")
    @Operation(summary = "admin-message-cancel", description = "取消公開留言")
    public ServerResponseEntity cancel(@PathVariable("id") Long id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(MessageStatus.CANCEL.value());
        messageService.updateById(message);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@pms.hasPermission('admin:message:delete')")
    @Operation(summary = "admin-message-delete", description = "刪除留言")
    public ServerResponseEntity delete(@PathVariable("ids") Long[] ids) {
        messageService.removeByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }
}
