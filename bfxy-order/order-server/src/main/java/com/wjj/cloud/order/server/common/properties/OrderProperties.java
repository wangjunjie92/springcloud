package com.wjj.cloud.order.server.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @Author: wangjunjie 2019/4/8 16:15
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/8 16:15
 */

@ConfigurationProperties(prefix = "bfxy.order")
public class OrderProperties {

    private RocketMQProperties rocketMQ=new RocketMQProperties();

    public RocketMQProperties getRocketMQ() {
        return rocketMQ;
    }

    public void setRocketMQ(RocketMQProperties rocketMQ) {
        this.rocketMQ = rocketMQ;
    }
}
