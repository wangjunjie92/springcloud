package com.wjj.cloud.payb.server.serivce.consumer;

import com.wjj.cloud.payb.server.dao.PlatformAccountMapper;
import com.wjj.cloud.payb.server.entity.PlatformAccount;
import com.wjj.cloud.payb.server.util.GsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjunjie 2019/3/19 15:35
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/19 15:35
 */

@Component
public class PayConsumer {

    private DefaultMQPushConsumer consumer;

    private static final String NAMESERVER = "39.106.193.32:9876";

    private static final String CONSUMER_GROUP_NAME = "cloud_pay_group";

    public static final String TX_PAY_TOPIC = "cloud_pay_topic";

    public static final String TX_PAY_TAGS = "pay";

    @Autowired
    private PlatformAccountMapper platformAccountMapper;

    private PayConsumer() {
        try {
            this.consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
            this.consumer.setConsumeThreadMin(10);
            this.consumer.setConsumeThreadMax(30);
            this.consumer.setNamesrvAddr(NAMESERVER);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe(TX_PAY_TOPIC,TX_PAY_TAGS);
            this.consumer.registerMessageListener(new MessageListenerConcurrently4Pay());
            this.consumer.start();
        }catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    class MessageListenerConcurrently4Pay implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg=msgs.get(0);
            try {
                String topic = msg.getTopic();
                String tags=msg.getTags();
                String keys = msg.getKeys();
                String body= new String(msg.getBody(),RemotingHelper.DEFAULT_CHARSET);
                System.err.println("收到事务消息, topic: " + topic + ", tags: " + tags + ", keys: " + keys + ", body: " + body);

                Map<String,Object> paramsBody = GsonUtil.GsonToBean(body, Map.class);
                String userId = (String)paramsBody.get("userId");	// customer userId
                String accountId = (String)paramsBody.get("accountId");	//customer accountId
                String orderId = (String)paramsBody.get("orderId");	// 	统一的订单
                //BigDecimal money = (BigDecimal)paramsBody.get("money");	//	当前的收益款

                PlatformAccount pa = platformAccountMapper.selectByPrimaryKey("platform001");
                pa.setCurrentBalance(pa.getCurrentBalance().add(new BigDecimal(50)));
                Date currentTime = new Date();
                pa.setVersion(pa.getVersion() + 1);
                pa.setDateTime(currentTime);
                pa.setUpdateTime(currentTime);
                platformAccountMapper.updateByPrimaryKeySelective(pa);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                //msg.getReconsumeTimes();
                //	如果处理多次操作还是失败, 记录失败日志（做补偿 回顾 人工处理）
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
