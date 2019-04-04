package com.wjj.cloud.paya.server.service.producer;

import com.wjj.cloud.paya.server.util.GsonUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: wangjunjie 2019/3/19 14:53
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/19 14:53
 */

@Service
public class CallbackService {

    public static final String CALLBACK_PAY_TOPIC = "cloud_callback_pay_topic";

    public static final String CALLBACK_PAY_TAGS = "cloud_callback_pay";

    @Autowired
    private SyncProducer syncProducer;

    public void sendOKMessage(String orderId,String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderId", orderId);
        params.put("status", "2");	//ok

        String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
        Message message = new Message(CALLBACK_PAY_TOPIC,CALLBACK_PAY_TAGS,
                keys,GsonUtil.GsonString(params).getBytes());
        SendResult result = syncProducer.sendMessage(message);
    }
}
