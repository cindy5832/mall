package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.HotSearch;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.HotSearchService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/hotSearch")
public class HotSearchController {
    @Autowired
    private HotSearchService hotSearchService;

    @Operation(summary = "admin-hotSearch-page", description = "熱搜分頁查詢")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:hotSearch:page')")
    public ServerResponseEntity<IPage<HotSearch>> page(HotSearch hotSearch, PageParam<HotSearch> page) {
        IPage<HotSearch> iPage = hotSearchService.page(page, new LambdaQueryWrapper<HotSearch>()
                .eq(HotSearch::getShopId, SecurityUtils.getSysUser().getShopId())
                .like(StrUtil.isNotBlank(hotSearch.getContent()), HotSearch::getContent, hotSearch.getContent())
                .like(StrUtil.isNotBlank(hotSearch.getTitle()), HotSearch::getTitle, hotSearch.getTitle())
                .eq(hotSearch.getStatus() != null, HotSearch::getStatus, hotSearch.getStatus())
                .orderByAsc(HotSearch::getSeq)
        );
        return ServerResponseEntity.success(iPage);
    }

    @Operation(summary = "admin-hotSearch-info", description = "獲取熱搜訊息")
    @GetMapping("/info/{id}")
    public ServerResponseEntity<HotSearch> info(@PathVariable("id") Long id) {
        HotSearch hotSearch = hotSearchService.getById(id);
        return ServerResponseEntity.success(hotSearch);
    }

    @Operation(summary = "admin-hotSearch-save", description = "保存熱搜")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin:hotSearch:save')")
    public ServerResponseEntity save(@Valid @RequestBody HotSearch hotSearch) {
        hotSearch.setRecDate(new Date());
        hotSearch.setShopId(SecurityUtils.getSysUser().getShopId());
        hotSearchService.save(hotSearch);
        // 清除緩存
        hotSearchService.removeHotSearchDtoCatchByShopId(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:hotSearch:update')")
    public ServerResponseEntity update(@RequestBody @Valid HotSearch hotSearch) {
        hotSearchService.updateById(hotSearch);
        // 清除緩存
        hotSearchService.removeHotSearchDtoCatchByShopId(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }

    @Operation(summary = "admin-hotSearch-delete", description = "刪除熱搜")
    @DeleteMapping("delete")
    @PreAuthorize(("@pms.hasPermission('admin:hotSearch:delete')"))
    public ServerResponseEntity delete(@RequestBody List<Long> ids) {
        hotSearchService.removeByIds(ids);
        // 清除緩存
        hotSearchService.removeHotSearchDtoCatchByShopId(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }
}
