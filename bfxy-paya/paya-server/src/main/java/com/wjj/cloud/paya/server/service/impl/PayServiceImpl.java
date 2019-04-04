package com.wjj.cloud.paya.server.service.impl;

import com.wjj.cloud.paya.server.dao.CustomerAccountMapper;
import com.wjj.cloud.paya.server.model.entity.CustomerAccount;
import com.wjj.cloud.paya.server.service.PayService;
import com.wjj.cloud.paya.server.service.producer.CallbackService;
import com.wjj.cloud.paya.server.service.producer.TransactionProducer;
import com.wjj.cloud.paya.server.util.GsonUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangjunjie 2019/3/18 16:38
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/18 16:38
 */

@Service
public class PayServiceImpl implements PayService {

    public static final String TX_PAY_TOPIC = "tx_pay_topic";

    public static final String TX_PAY_TAGS = "pay";

    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Autowired
    private TransactionProducer transactionProducer;

    @Autowired
    private CallbackService callbackService;

    @Override
    public String payment(String userId, String orderId, String accountId, double money) {
        String paymentRet = "";
        try {
            BigDecimal payMoney = new BigDecimal(money);
            CustomerAccount old = customerAccountMapper.selectByPrimaryKey(accountId);
            BigDecimal currentBalance = old.getCurrentBalance();
            int currentVersion = old.getVersion();

            BigDecimal newBalance = currentBalance.subtract(payMoney);
            if (newBalance.doubleValue()>0) {
                String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
                Map<String, Object> params = new HashMap<>();
                params.put("userId",userId);
                params.put("orderId", orderId);
                params.put("accountId", accountId);
                params.put("money", money);

                Message message = new Message(TX_PAY_TOPIC, TX_PAY_TAGS, keys, GsonUtil.GsonString(params).getBytes());
                //	可能需要用到的参数
                params.put("payMoney", payMoney);
                params.put("newBalance", newBalance);
                params.put("currentVersion", currentVersion);

                //	同步阻塞
                CountDownLatch countDownLatch = new CountDownLatch(1);
                params.put("currentCountDown", countDownLatch);
                //	消息发送并且 本地的事务执行
                TransactionSendResult sendResult = transactionProducer.sendMessage(message, params);

                countDownLatch.await(3000,TimeUnit.MILLISECONDS);
                if (sendResult.getSendStatus() == SendStatus.SEND_OK &&
                        sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE){
                    //	回调order通知支付成功消息
                    callbackService.sendOKMessage(orderId,userId);
                    paymentRet = "支付成功!";
                }else {
                    paymentRet = "支付失败!";
                }
            }else {
                paymentRet = "余额不足!";
            }

        }catch (Exception e) {
            e.printStackTrace();
            paymentRet = "支付失败!";
        }
        return paymentRet;
    }
}
