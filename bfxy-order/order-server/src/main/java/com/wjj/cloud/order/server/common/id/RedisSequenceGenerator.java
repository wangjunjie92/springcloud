package com.wjj.cloud.order.server.common.id;

import com.wjj.cloud.bfxy.common.util.StringUtil;
import com.wjj.cloud.order.server.common.constants.DbAndTableEnum;
import com.wjj.cloud.order.server.common.constants.ShardingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: wangjunjie 2019/4/4 15:01
 * @Description: 订单id生成工具类
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/4 15:01
 */

@Component
public class RedisSequenceGenerator implements SequenceGenerator {

    /**序列生成器key前缀*/
    public static String LOGIC_TABLE_NAME = "orderId:redis:";

    public static int SEQUENCE_LENGTH = 5;

    public static int sequence_max = 90000;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String getOrderId(DbAndTableEnum targetEnum,int dbIndex,int tbIndex) {
        //拼接key前缀
        String redisKeySuffix = new StringBuffer(targetEnum.getTableName())
                .append("_")
                .append("dbIndex")
                .append(StringUtil.fillZero(String.valueOf(dbIndex),ShardingConstant.DB_SUFFIX_LENGTH))
                .append("_tbIndex")
                .append(StringUtil.fillZero(String.valueOf(tbIndex), ShardingConstant.TABLE_SUFFIX_LENGTH))
                .append("_")
                .append(targetEnum.getShardingKey()).toString();
        String increKey = new StringBuilder(LOGIC_TABLE_NAME).append(redisKeySuffix).toString();
        long sequenceId = redisTemplate.opsForValue().increment(increKey,1);
        //达到指定值重置序列号，预留后10000个id以便并发时缓冲
        if (sequenceId >= sequence_max) {
            redisTemplate.delete(increKey);
        }
        // 返回序列值，位数不够前补零
        return StringUtil.fillZero(String.valueOf(sequenceId), SEQUENCE_LENGTH);
    }
}
