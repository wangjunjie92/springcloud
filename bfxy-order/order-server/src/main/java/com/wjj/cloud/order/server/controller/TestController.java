package com.wjj.cloud.order.server.controller;

import com.wjj.cloud.product.client.ProductStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wangjunjie 2019/3/12 13:58
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/12 13:58
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProductStoreClient productStoreClient;

    @GetMapping("/test1")
    public String test1(String supplierId, String goodsId) {
        int i = productStoreClient.selectStoreCount(supplierId, goodsId);
        return "success:......count:"+i;
    }

    @GetMapping("/test2")
    public String test2(String supplierId, String goodsId, HttpServletRequest request) throws InterruptedException {
        String token = request.getHeader("token");
        int i = productStoreClient.selectVersion(supplierId, goodsId);
        return "success:......count:"+i;
    }


    @GetMapping("/test3")
    public String test3(String pid) throws InterruptedException {
        return "success:......pid:"+pid;
    }


}
