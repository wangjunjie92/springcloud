package com.wjj.cloud.payb.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: wangjunjie 2019/3/20 22:37
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/20 22:37
 */

@FeignClient(name = "payb")
public interface PaybServiceClient {
}
