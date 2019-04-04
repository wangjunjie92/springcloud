package com.wjj.cloud.order.server.service.impl;


import com.wjj.cloud.order.server.common.constants.OrderStatus;
import com.wjj.cloud.order.server.dao.OrderMapper;
import com.wjj.cloud.order.server.entity.Order;
import com.wjj.cloud.order.server.service.OrderService;
import com.wjj.cloud.order.server.service.producer.OrderlyProducer;
import com.wjj.cloud.order.server.util.GsonUtil;
import com.wjj.cloud.product.client.ProductStoreClient;
import com.wjj.cloud.product.common.ReduceStoreInput;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: wangjunjie 2019/1/2 21:34
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/1/2 21:34
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductStoreClient productStoreClient;
    @Autowired
    private OrderlyProducer orderlyProducer;


    @Override
    public boolean createOrder(String cityId, String platformId, String userId, String supplierId, String goodsId) {
        boolean flag = true;
        try {
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString().substring(0, 32));
            order.setOrderType("1");
            order.setCityId(cityId);
            order.setPlatformId(platformId);
            order.setUserId(userId);
            order.setSupplierId(supplierId);
            order.setGoodsId(goodsId);
            order.setOrderStatus(OrderStatus.ORDER_CREATED.getValue());
            order.setRemark("");

            Date currentTime = new Date();
            order.setCreateBy("admin");
            order.setCreateTime(currentTime);
            order.setUpdateBy("admin");
            order.setUpdateTime(currentTime);

            int currentVersion = productStoreClient.selectVersion(supplierId, goodsId);
            ReduceStoreInput reduceStoreInput = new ReduceStoreInput();
            reduceStoreInput.setVersion(currentVersion);
            reduceStoreInput.setSupplierId(supplierId);
            reduceStoreInput.setGoodsId(goodsId);
            reduceStoreInput.setUpdateBy("admin");
            reduceStoreInput.setUpdateTime(currentTime);
            int updateRetCount = productStoreClient.reduceStoreCountByVersion(reduceStoreInput);

            if(updateRetCount == 1) {
                // DOTO:	如果出现SQL异常 入库失败, 那么要对 库存的数量 和版本号进行回滚操作
                orderMapper.insertSelective(order);
            }
            //	没有更新成功 1 高并发时乐观锁生效   2 库存不足
            else if (updateRetCount == 0){
                flag = false;	//	下单标识失败
                int currentStoreCount = productStoreClient.selectStoreCount(supplierId, goodsId);
                if(currentStoreCount == 0) {
                    //{flag:false , messageCode: 003 , message: 当前库存不足}
                    System.err.println("-----当前库存不足...");
                } else {
                    //{flag:false , messageCode: 004 , message: 乐观锁生效}
                    System.err.println("-----乐观锁生效...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 	具体捕获的异常是什么异常
            flag = false;
        }

        return flag;
    }

    public static final String PKG_TOPIC = "cloud_pkg_topic";

    public static final String PKG_TAGS = "cloud_pkg";

    @Override
    public void sendOrderlyMessage4Pkg(String userId, String orderId) {
        List<Message> messageList = new ArrayList<>();

        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("userId", userId);
        param1.put("orderId", orderId);
        param1.put("text", "创建包裹操作---1");

        String key1 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message1 = new Message(PKG_TOPIC, PKG_TAGS, key1, GsonUtil.GsonString(param1).getBytes());

        messageList.add(message1);


        Map<String, Object> param2 = new HashMap<String, Object>();
        param2.put("userId", userId);
        param2.put("orderId", orderId);
        param2.put("text", "发送物流通知操作---2");

        String key2 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message2 = new Message(PKG_TOPIC, PKG_TAGS, key2, GsonUtil.GsonString(param2).getBytes());

        messageList.add(message2);

        //	顺序消息投递 是应该按照 供应商ID 与topic 和 messagequeueId 进行绑定对应的
        //  supplier_id

        Order order = orderMapper.selectByPrimaryKey(orderId);
        int messageQueueNumber = Integer.parseInt(order.getSupplierId());

        //对应的顺序消息的生产者 把messageList 发出去
        orderlyProducer.sendOrderlyMessages(messageList, messageQueueNumber);
    }


}
