package com.wjj.cloud.paya.server.controller;

import com.wjj.cloud.paya.server.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paya")
public class PayController {

	@Autowired
	private PayService payService;
	
	@RequestMapping("/pay")
	public String pay(@RequestParam("userId")String userId,
			@RequestParam("orderId")String orderId,
			@RequestParam("accountId")String accountId,
			@RequestParam("money")Double money) throws Exception {
		return payService.payment(userId, orderId, accountId, money);
	}
	
}
