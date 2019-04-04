package com.wjj.cloud.order.server.service;

public interface OrderService {

	boolean createOrder(String cityId, String platformId, String userId, String supplierId, String goodsId);

	void sendOrderlyMessage4Pkg(String userId, String orderId);

}
