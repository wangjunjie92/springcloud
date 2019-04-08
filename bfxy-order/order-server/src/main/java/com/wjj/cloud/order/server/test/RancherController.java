package com.wjj.cloud.order.server.test;

import com.wjj.cloud.bfxy.common.msg.ApiResult;
import com.wjj.cloud.order.server.dao.OrderMapper;
import com.wjj.cloud.order.server.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangjunjie 2019/4/7 22:04
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/7 22:04
 */

@RestController
@RequestMapping("/test")
public class RancherController {

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/rancher1")
    public ApiResult testRancher1() {
        return ApiResult.onSuccess("success");
    }

    @GetMapping("/rancher2")
    public ApiResult testRancher2() {
        Order order = orderMapper.selectByPrimaryKey("8848a");
        return ApiResult.onSuccess(order);
    }
}
