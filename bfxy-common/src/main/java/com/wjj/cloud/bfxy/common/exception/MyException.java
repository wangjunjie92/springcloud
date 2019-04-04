package com.wjj.cloud.bfxy.common.exception;

import lombok.Getter;

/**
 * @Author: wangjunjie 11:20$ 2018/4/16$
 * @Description:$params$
 * @Version: 1.0.0
 * @Modified By: xxx 11:20$ 2018/4/16$
 */

@Getter
public class MyException extends RuntimeException {

    private String message;

    private int code;

    public MyException(String message) {
        super(message);
        this.code=1010;
        this.message=message;
    }


}
