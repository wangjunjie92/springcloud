package com.wjj.cloud.order.server.dao;



import com.wjj.cloud.order.server.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    List<Order> selectByUserId(String userId);

    List<Order> selectByUsers(@Param("userIds")List<String> userIds);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	int updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus,
                          @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);
}