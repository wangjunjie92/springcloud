package com.wjj.cloud.paya.server.service;

public interface PayService {

	String payment(String userId, String orderId, String accountId, double money);
}
