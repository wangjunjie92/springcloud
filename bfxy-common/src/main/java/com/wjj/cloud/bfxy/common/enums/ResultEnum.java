package com.wjj.cloud.bfxy.common.enums;

import lombok.Getter;

/**
 * @Author: wangjunjie 21:15$ 2018/4/23$
 * @Description:$params$
 * @Version: 1.0.0
 * @Modified By: xxx 21:15$ 2018/4/23$
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    END(100,"活动结束");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
