package com.demo.mall.common.handler;

import cn.hutool.core.util.CharsetUtil;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class HttpHandler {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> void printServerResponseToWeb(ServerResponseEntity<T> serverResponseEntity) {
        if (serverResponseEntity == null) {
            log.info("print obj is null");
            return;
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.error("requestAttributes is null, can not print to web");
            return;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        if (response == null) {
            log.error("HttpServletResponse is null, can not print to web");
            return;
        }

        log.error("response error: {}", serverResponseEntity.getMsg());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(serverResponseEntity));
        }catch (IOException e) {
            throw new ShopException("IO exception", e);
        }
    }

    public <T> void printServerResponseToWeb(ShopException shopException) {
        if (shopException == null) {
            log.info("print obj is null");
            return;
        }
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(shopException.getCode());
        serverResponseEntity.setMsg(shopException.getMessage());
        printServerResponseToWeb(serverResponseEntity);
    }
}
