package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.enums.AreaLevelEnum;
import com.demo.mall.bean.model.Area;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.AreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Tag(name = "admin-area-page", description = "區域分頁查詢")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:area:page')")
    public ServerResponseEntity<IPage<Area>> page(Area area, PageParam<Area> page) {
        IPage<Area> sysUserPage = areaService.page(page, new LambdaQueryWrapper<Area>());
        return ServerResponseEntity.success(sysUserPage);
    }

    @Tag(name = "admin-area-list", description = "縣市列表")
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('admin:area:list')")
    public ServerResponseEntity<List<Area>> list(Area area) {
        List<Area> areas = areaService.list(
                new LambdaQueryWrapper<Area>()
                        .like(area.getAreaName() != null, Area::getAreaName, area.getAreaName())
        );
        return ServerResponseEntity.success(areas);
    }

    @Tag(name = "admin-area-listByPid", description = "根據父級id獲取列表")
    @GetMapping("/listByPid")
    public ServerResponseEntity<List<Area>> listByPid(Long pid) {
        List<Area> list = areaService.listByPid(pid);
        return ServerResponseEntity.success(list);
    }

    @Tag(name = "admin-area-info", description = "獲取訊息")
    @GetMapping("info/{id}")
    @PreAuthorize(("@pms.hasPermission('admin:area:info')"))
    public ServerResponseEntity<Area> info(@PathVariable("id") Long id) {
        Area area = areaService.getById(id);
        return ServerResponseEntity.success(area);
    }

    @Tag(name = "admin-area-save", description = "保存區域訊息")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin:area:save')")
    public ServerResponseEntity save(@Valid @RequestBody Area area) {
        if (area.getParentId() != null) {
            Area parentArea = areaService.getById(area.getParentId());
            area.setLevel(parentArea.getLevel() + 1);
            areaService.removeAreaCacheByParentId(area.getParentId());
        }
        areaService.save(area);
        return ServerResponseEntity.success();
    }

    @Tag(name = "admin-area-updateInfo", description = "修改區域資訊")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:area:update')")
    public ServerResponseEntity update(@Valid @RequestBody Area area) {
        Area areaDb = areaService.getById(area.getAreaId());
        // 若區域級別為 1 & 2 則不能修改級別，也不能變為別人的下級
        if (Objects.equals(areaDb.getLevel(), AreaLevelEnum.FIRST_LEVEL.value())
                && !Objects.equals(area.getLevel(), AreaLevelEnum.FIRST_LEVEL.value())) {
            throw new ShopException("不能修改一級行政區域級別");
        }
        if (Objects.equals(areaDb.getLevel(), AreaLevelEnum.SECOND_LEVEL.value())
                && !Objects.equals(area.getLevel(), AreaLevelEnum.SECOND_LEVEL.value())) {
            throw new ShopException("不能修改第二級行政區域級別");
        }
        hasSameName(area);
        areaService.updateById(area);
        areaService.removeAreaCacheByParentId(area.getParentId());
        return ServerResponseEntity.success();
    }

    @Tag(name = "admin-area-delete", description = "刪除區域")
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('admin:area:delete')")
    public ServerResponseEntity delete(@PathVariable Long id) {
        Area area = areaService.getById(id);
        areaService.removeById(id);
        areaService.removeAreaCacheByParentId(area.getParentId());
        return ServerResponseEntity.success();
    }

    private void hasSameName(Area area) {
        long count = areaService.count(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId, area.getParentId())
                .eq(Area::getAreaName, area.getAreaName())
                .ne(Objects.nonNull(area.getAreaId()) && !Objects.equals(area.getAreaId(), 0L)
                        , Area::getAreaId, area.getAreaId()));
        if (count > 0) {
            throw new ShopException("該地區已存在");
        }
    }

}
