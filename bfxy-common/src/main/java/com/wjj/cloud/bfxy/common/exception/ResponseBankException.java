package com.wjj.cloud.bfxy.common.exception;


import com.wjj.cloud.bfxy.common.enums.ResultEnum;

/**
 * Created by 廖师兄
 * 2017-08-03 23:58
 */

public class ResponseBankException extends RuntimeException {

    private String message;

    private int code;

    public ResponseBankException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.message = resultEnum.getMessage();
        this.code = resultEnum.getCode();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
