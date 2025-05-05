package com.demo.mall.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.ProdCommDataDto;
import com.demo.mall.bean.app.dto.ProdCommDto;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.bean.param.ProdCommParam;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.ProdCommService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/prodComm")
@Tag(name = "prod-comm", description = "商品評論")
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @GetMapping("/prodCommData")
    @Operation(summary = "prod-count", description = "根據商品id獲取評論數據")
    public ServerResponseEntity<ProdCommDataDto> getProdCommData(Long prodId) {
        return ServerResponseEntity.success(prodCommService.getProdCommDataByProdId(prodId));
    }

    @GetMapping("/prodCommPageByUser")
    @Operation(summary = "common-page", description = "根據用戶返回評論數據")
    public ServerResponseEntity<IPage<ProdCommDto>> getProdCommPage(PageParam page) {
        return ServerResponseEntity.success(prodCommService.getProdCommDtoPageByUserId(page, SecurityUtils.getUser().getUserId()));
    }

    @GetMapping("/prodCommPageByProd")
    @Operation(summary = "prod-comm-page", description = "根據商品id返回評論分頁數據")
    @Parameters({
            @Parameter(name = "prodId", description = "商品id", required = true),
            @Parameter(name = "evaluate", description = "-1或null 全部，0好評 1中評 2差評 3有圖", required = true),
    })
    public ServerResponseEntity<IPage<ProdCommDto>> getProdCommPageByProd(PageParam page, Long prodId, Integer evaluate) {
        return ServerResponseEntity.success(prodCommService.getProdCommDtoPageByProdId(page, prodId, evaluate));
    }

    @PostMapping("/addComm")
    @Operation(summary = "addComm", description = "添加評論")
    public ServerResponseEntity addComm(@RequestBody ProdCommParam prodCommParam) {
        ProdComm prodComm = new ProdComm();
        prodComm.setProdId(prodCommParam.getProdId());
        prodComm.setOrderItemId(prodCommParam.getOrderItemId());
        prodComm.setUserId(SecurityUtils.getUser().getUserId());
        prodComm.setScore(prodCommParam.getScore());
        prodComm.setContent(prodCommParam.getContent());
        prodComm.setPics(prodCommParam.getPics());
        prodComm.setIsAnonymous(prodCommParam.getIsAnonymous());
        prodComm.setRecTime(new Date());
        prodComm.setStatus(0);
        prodComm.setEvaluate(prodCommParam.getEvaluate());
        prodCommService.save(prodComm);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "comm-delete", description = "根據id刪除評論")
    public ServerResponseEntity deleteComm(Long commId) {
        return ServerResponseEntity.success(prodCommService.removeById(commId));
    }
}
