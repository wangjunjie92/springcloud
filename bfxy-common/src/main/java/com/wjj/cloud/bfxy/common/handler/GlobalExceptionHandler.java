package com.wjj.cloud.bfxy.common.handler;

import com.wjj.cloud.bfxy.common.exception.MyException;
import com.wjj.cloud.bfxy.common.msg.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wangjunjie 2019/3/28 17:31
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/28 17:31
 */

@ControllerAdvice("com.wjj.cloud")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ApiResult clientTokenExceptionHandler(MyException ex) {
        log.error(ex.getMessage(),ex);
        return ApiResult.onError(ex.getMessage(),ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult otherExceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(),ex);
        return ApiResult.onError(ex.getMessage(),500);
    }
}
