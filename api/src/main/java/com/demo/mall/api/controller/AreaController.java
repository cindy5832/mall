package com.demo.mall.api.controller;

import com.demo.mall.bean.model.Area;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/p/area")
@Tag(name = "area", description = "省區api")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/listByPid")
    @Operation(summary = "api-area", description = "根據省市區id獲取地址訊息")
    public ServerResponseEntity<List<Area>> listByPid(Long pid) {
        List<Area> list = areaService.listByPid(pid);
        return ServerResponseEntity.success(list);
    }
}
