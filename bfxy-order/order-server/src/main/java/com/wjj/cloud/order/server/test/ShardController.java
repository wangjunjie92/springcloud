package com.wjj.cloud.order.server.test;

import com.wjj.cloud.bfxy.common.msg.ApiResult;
import com.wjj.cloud.order.server.common.constants.DbAndTableEnum;
import com.wjj.cloud.order.server.common.constants.OrderStatus;
import com.wjj.cloud.order.server.common.id.KeyGenerator;
import com.wjj.cloud.order.server.dao.OrderMapper;
import com.wjj.cloud.order.server.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: wangjunjie 2019/4/8 11:13
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/8 11:13
 */

@RestController
@RequestMapping("/shard")
public class ShardController {

    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/test1")
    public ApiResult addOrder(int pid) {
        //String userId = keyGenerator.generateKey(DbAndTableEnum.T_USER, String.valueOf(pid));
        String orderId = keyGenerator.generateKey(DbAndTableEnum.T_ORDER, String.valueOf(pid));
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderType("美女");
        order.setCityId("大明宫");
        order.setPlatformId("abcd");
        order.setUserId(String.valueOf(pid));
        order.setSupplierId("2008");
        order.setGoodsId("2008");
        order.setOrderStatus(OrderStatus.ORDER_CREATED.getValue());
        order.setRemark("");

        Date currentTime = new Date();
        order.setCreateBy("admin");
        order.setCreateTime(currentTime);
        order.setUpdateBy("admin");
        order.setUpdateTime(currentTime);
        orderMapper.insert(order);
        return ApiResult.onSuccess();
    }

    @GetMapping("/test2")
    public ApiResult getOrderByPid(int pid) {
        List<Order> orders = orderMapper.selectByUserId(String.valueOf(pid));
        return ApiResult.onSuccess(orders);
    }

    @GetMapping("/test3")
    public ApiResult getOrder() {
        List<String> userIds = new ArrayList<>();
        userIds.add("1");
        userIds.add("2");
        userIds.add("11");
        userIds.add("14");
        userIds.add("21");
        userIds.add("428");
        userIds.add("100");
        userIds.add("25");
        userIds.add("110");
        userIds.add("1100");
        userIds.add("aaa");
        userIds.add("bbb");
        List<Order> orders = orderMapper.selectByUsers(userIds);
        return ApiResult.onSuccess(orders);
    }

}
