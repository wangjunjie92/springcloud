package com.wjj.cloud.order.server.controller;

import com.wjj.cloud.order.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangjunjie 2019/3/18 14:45
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/18 14:45
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(String cityId, String platformId,
                              String userId, String supplierId, String goodsId) throws Exception {
        boolean order = orderService.createOrder(cityId, platformId, userId, supplierId, goodsId);
        if (order) {
            return "下单成功";
        }else {
            return "下单失败";
        }
    }
}
