package com.wjj.cloud.order.server.service.consumer;

import com.wjj.cloud.order.server.common.constants.OrderStatus;
import com.wjj.cloud.order.server.common.properties.OrderProperties;
import com.wjj.cloud.order.server.dao.OrderMapper;
import com.wjj.cloud.order.server.service.OrderService;
import com.wjj.cloud.order.server.util.GsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjunjie 2019/3/18 15:53
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/18 15:53
 */

@Component
public class OrderConsumer implements InitializingBean {

    @Autowired
    private OrderProperties orderProperties;

    private DefaultMQPushConsumer consumer;

    public static final String CALLBACK_PAY_TOPIC = "cloud_callback_pay_topic";

    public static final String CALLBACK_PAY_TAGS = "cloud_callback_pay";

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String nameServer = orderProperties.getRocketMQ().getAddress();
        consumer.setNamesrvAddr(nameServer);
        consumer.start();
    }

    class MessageListenerConcurrently4Pay implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg = msgs.get(0);
            try {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                String keys = msg.getKeys();
                System.err.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags + "keys :" + keys + ", msg : " + msgBody);
                String orignMsgId = msg.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
                System.err.println("orignMsgId: " + orignMsgId);

                //通过keys 进行去重表去重 或者使用redis进行去重???? --> 不需要
                Map<String, Object> body = GsonUtil.GsonToBean(msgBody, Map.class);
                String orderId = (String) body.get("orderId");
                String userId = (String) body.get("userId");
                String status = (String)body.get("status");

                Date currentTime = new Date();

                if(status.equals(OrderStatus.ORDER_PAYED.getValue())) {
                    int count  = orderMapper.updateOrderStatus(orderId, status, "admin", currentTime);
                    if(count == 1) {
                        orderService.sendOrderlyMessage4Pkg(userId, orderId);
                        System.out.println("order..........支付成功");
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    private OrderConsumer()  {
        try {
            consumer = new DefaultMQPushConsumer(CALLBACK_PAY_TOPIC);
            consumer.setConsumeThreadMax(10);
            consumer.setConsumeThreadMax(30);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.subscribe(CALLBACK_PAY_TOPIC, CALLBACK_PAY_TAGS);
            consumer.registerMessageListener(new MessageListenerConcurrently4Pay());
        }catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
