package com.wjj.cloud.apigateway.predicate;

import lombok.Data;

import java.time.LocalTime;

/**
 * @Author: wangjunjie 2019/8/15 10:37
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/8/15 10:37
 */

@Data
public class TimeBeweenConfig {

    private LocalTime start;
    private LocalTime end;
}
