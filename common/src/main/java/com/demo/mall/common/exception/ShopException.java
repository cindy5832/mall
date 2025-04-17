package com.demo.mall.common.exception;

import com.demo.mall.common.response.ResponseEnum;
import com.demo.mall.common.response.ServerResponseEntity;
import lombok.Data;

import java.io.Serial;

@Data
public class ShopException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4137688758944857209L;

    // http 狀態碼
    private String code;

    private Object object;

    private ServerResponseEntity<?> serverResponseEntity;

    public ShopException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.value();
    }

    public ShopException(ResponseEnum responseEnum, String msg) {
        super(msg);
        this.code = responseEnum.value();
    }

    public ShopException(ServerResponseEntity<?> serverResponseEntity) {
        this.serverResponseEntity = serverResponseEntity;
    }

    public ShopException(String msg){
        super(msg);
        this.code = ResponseEnum.SHOW_FAIL.value();
    }

    public ShopException(String msg, Object object){
        super(msg);
        this.code =ResponseEnum.SHOW_FAIL.value();
        this.object = object;
    }
}
