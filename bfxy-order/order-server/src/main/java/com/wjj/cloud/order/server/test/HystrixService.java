package com.wjj.cloud.order.server.test;

import org.springframework.stereotype.Service;

/**
 * @Author: wangjunjie 2019/3/23 15:44
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/23 15:44
 */

@Service
public class HystrixService {

    public void testHystrixService(int i) {
        if (i==6) {
            System.out.println("bbbbbbbbb");
            throw new RuntimeException();
        }
        System.out.println("Hystrix....test1");
    }
}
