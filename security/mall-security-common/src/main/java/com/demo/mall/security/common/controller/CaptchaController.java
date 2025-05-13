package com.demo.mall.security.common.controller;

import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.demo.mall.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
@Tag(name = "verification", description = "驗證碼")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/get")
    public ServerResponseEntity<ResponseModel> get(@RequestBody CaptchaVO captchaVO) {
        return ServerResponseEntity.success(captchaService.get(captchaVO));
    }

    @PostMapping("/check")
    public ServerResponseEntity<ResponseModel> check(@RequestBody CaptchaVO captchaVO) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel = captchaService.check(captchaVO);
        }catch (Exception e){
            return ServerResponseEntity.success(ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_COORDINATE_ERROR));
        }
        return ServerResponseEntity.success(responseModel);
    }
}
