package com.wjj.cloud.bfxy.common.exception;



import com.wjj.cloud.bfxy.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author: wangjunjie 22:24$ 2018/4/23$
 * @Description:$params$
 * @Version: 1.0.0
 * @Modified By: xxx 22:24$ 2018/4/23$
 */
@Getter
public class PageException extends RuntimeException {

    private int code;

    private String message;

    public PageException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
}
