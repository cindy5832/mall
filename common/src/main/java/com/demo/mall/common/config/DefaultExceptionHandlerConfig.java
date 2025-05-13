package com.demo.mall.common.config;

import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ResponseEnum;
import com.demo.mall.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RestControllerAdvice
@Tag(name = "exception-handler", description = "自定義錯誤處理器")
public class DefaultExceptionHandlerConfig {

    @ExceptionHandler({
            MethodArgumentNotValidException.class, BindException.class
    })
    public ResponseEntity<ServerResponseEntity<List<String>>> methodArgumentNotValidExceptionHandler(Exception e) {
        log.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ServerResponseEntity.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID));
        }
        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponseEntity.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID, defaultMessages));
    }

    @ExceptionHandler(ShopException.class)
    public ResponseEntity<ServerResponseEntity<?>> unauthorizedExceptionHandler(ShopException e) {
        log.error("mall4jExceptionHandler", e);
        ServerResponseEntity<?> serverResponseEntity = e.getServerResponseEntity();
        if (serverResponseEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(serverResponseEntity);
        }
        // 失敗返回消息，狀態碼為直接顯示消息的狀態碼
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponseEntity<Object>> exceptionHandler(Exception e) {
        if (e instanceof NoResourceFoundException) {
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.showFailMsg(e.getMessage()));
        }
        log.error("exceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(ResponseEnum.EXCEPTION));
    }
}
