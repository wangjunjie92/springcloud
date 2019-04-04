package com.wjj.cloud.pkg.server.service.consumer;

import com.wjj.cloud.pkg.server.util.GsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangjunjie 2019/3/21 15:18
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/21 15:18
 */

@Component
public class PkgOrderlyConsumer {

    private DefaultMQPushConsumer consumer;

    public static final String PKG_TOPIC = "cloud_pkg_topic";

    public static final String PKG_TAGS = "cloud_pkg";

    public static final String NAMESERVER = "39.106.193.32:9876";

    public static final String CONSUMER_GROUP_NAME = "cloud_orderly_group";

    public PkgOrderlyConsumer() throws MQClientException {
        this.consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        this.consumer.setConsumeThreadMax(30);
        this.consumer.setConsumeThreadMin(10);
        this.consumer.setNamesrvAddr(NAMESERVER);
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        this.consumer.subscribe(PKG_TOPIC,PKG_TAGS);
        this.consumer.setMessageListener(new PkgOrderlyListener());
        this.consumer.start();
    }

    class PkgOrderlyListener implements MessageListenerOrderly {

        Random random = new Random();

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

            for (MessageExt msg: msgs) {
               try {
                   String topic = msg.getTopic();
                   String msgBody = new String(msg.getBody(), "utf-8");
                   String tags = msg.getTags();
                   String keys = msg.getKeys();
                   System.err.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags + "keys :" + keys + ", msg : " + msgBody);

                   Map<String, Object> body = GsonUtil.GsonToBean(msgBody, Map.class);
                   String orderId = (String) body.get("orderId");
                   String userId = (String) body.get("userId");
                   String text = (String)body.get("text");

                   //	模拟实际的业务耗时操作
                   //	PS: 创建包裹信息  、对物流的服务调用（异步调用）
                   TimeUnit.SECONDS.sleep(random.nextInt(3) + 1);

                   System.err.println("业务操作: " + text);
               }catch (Exception e) {
                   e.printStackTrace();
                   return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
               }
            }
            return ConsumeOrderlyStatus.SUCCESS;
        }
    }
}
