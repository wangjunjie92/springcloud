package com.wjj.cloud.order.server.common.id;

import com.wjj.cloud.order.server.common.constants.DbAndTableEnum;

/**
 * @Author: wangjunjie 2019/4/4 15:46
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/4 15:46
 */
public interface SequenceGenerator {

   String getOrderId(DbAndTableEnum targetEnum,int dbIndex,int tbIndex) ;
}
