package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.mall.bean.app.dto.UserAddrDto;
import com.demo.mall.bean.app.param.AddrParam;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.UserAddrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/p/address")
@Tag(name = "p-address", description = "地址")
public class AddrController {

    @Autowired
    private UserAddrService userAddrService;

    @GetMapping("/list")
    @Operation(summary = "api-addr-info", description = "獲取用戶的所有地址訊息")
    public ServerResponseEntity<List<UserAddrDto>> dvyList() {
        String userId = SecurityUtils.getUser().getUserId();
        List<UserAddr> userAddrs = userAddrService.list(
                new LambdaQueryWrapper<UserAddr>()
                        .eq(UserAddr::getUserId, userId)
                        .orderByDesc(UserAddr::getCommonAddr)
                        .orderByDesc(UserAddr::getUpdateTime)
        );
        return ServerResponseEntity.success(BeanUtil.copyToList(userAddrs, UserAddrDto.class));
    }

    @PostMapping("/addAddr")
    @Operation(summary = "api-addAddr", description = "新增用戶地址")
    public ServerResponseEntity<String> addAddr(@RequestBody @Valid AddrParam addrParam) {
        String userId = SecurityUtils.getUser().getUserId();
        if (addrParam.getAddrId() != null && addrParam.getAddrId() != 0) {
            return ServerResponseEntity.showFailMsg("該地址已存在");
        }
        long addrCount = userAddrService.count(
                new LambdaQueryWrapper<UserAddr>()
                .eq(UserAddr::getUserId, userId)
        );
        UserAddr userAddr = BeanUtil.copyProperties(addrParam, UserAddr.class);
        if (addrCount == 0){
            userAddr.setCommonAddr(1);
        }else {
            userAddr.setCommonAddr(0);
        }
        userAddr.setUserId(userId);
        userAddr.setStatus(1);
        userAddr.setCreateTime(new Date());
        userAddr.setUpdateTime(new Date());
        userAddrService.save(userAddr);

        if(userAddr.getCommonAddr() == 1){
            // 清楚默認地址緩存
            userAddrService.removeUserAddrByUserId(0L, userId);
        }
        return ServerResponseEntity.success("添加地址成功");
    }

    @PutMapping("/updateAddr")
    @Operation(summary = "api-updateAddr", description = "修改訂單用戶地址")
    public ServerResponseEntity<String> updateAddr(@RequestBody @Valid AddrParam addrParam) {
        String userId = SecurityUtils.getUser().getUserId();
        UserAddr dbUserAddr = userAddrService.getUserAddrByUserId(addrParam.getAddrId(), userId);
        if (dbUserAddr == null) {
            return ServerResponseEntity.showFailMsg("該地址已刪除");
        }
        UserAddr userAddr = BeanUtil.copyProperties(addrParam, UserAddr.class);
        userAddr.setUserId(userId);
        userAddr.setUpdateTime(new Date());
        userAddrService.updateById(userAddr);
        // 清除目前緩存
        userAddrService.removeUserAddrByUserId(addrParam.getAddrId(), userId);
        // 清除默認地址緩存
        userAddrService.removeUserAddrByUserId(0L, userId);
        return ServerResponseEntity.success("修改地址成功");
    }

    @DeleteMapping("/deleteAddr/{addrId}")
    @Operation(summary = "api-deleteAddr",description = "刪除訂單用戶地址")
    public ServerResponseEntity<String> deleteAddr(@PathVariable("addrId") Long addrId) {
        String userId = SecurityUtils.getUser().getUserId();
        UserAddr dbUserAddr = userAddrService.getUserAddrByUserId(addrId, userId);
        if (dbUserAddr == null) {
            return ServerResponseEntity.showFailMsg("該地址不存在");
        }
        if(dbUserAddr.getCommonAddr() == 1){
            return ServerResponseEntity.showFailMsg("默認地址無法刪除");
        }
        userAddrService.removeById(addrId);
        userAddrService.removeUserAddrByUserId(addrId, userId);
        return ServerResponseEntity.success("刪除地址成功");
    }

    @PutMapping("/defaultAddr/{addrId}")
    @Operation(summary = "defaultAddr", description = "根據addrId，設置默認地址")
    public ServerResponseEntity<String> defaultAddr(@PathVariable("addrId") Long addrId) {
        String userId = SecurityUtils.getUser().getUserId();
        userAddrService.updateDefaultUserAddr(addrId, userId);
        userAddrService.removeUserAddrByUserId(0L, userId);
        userAddrService.removeUserAddrByUserId(addrId, userId);
        return ServerResponseEntity.success("修改默認地址成功");
    }

    @GetMapping("/addrInfo/{addrId}")
    @Operation(summary = "api-addrInfo", description = "根據地址id獲取地址訊息")
    public ServerResponseEntity<UserAddrDto> addrInfo(@PathVariable("addrId") Long addrId) {
        String userId = SecurityUtils.getUser().getUserId();
        UserAddr userAddr = userAddrService.getUserAddrByUserId(addrId, userId);
        if (userAddr == null) {
            throw new ShopException("該地址不存在");
        }
        return ServerResponseEntity.success(BeanUtil.copyProperties(userAddr, UserAddrDto.class));
    }
}
