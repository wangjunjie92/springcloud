package com.wjj.cloud.order.server.test;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wjj.cloud.bfxy.common.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangjunjie 2019/3/22 14:50
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/22 14:50
 */

@RequestMapping("/hystrix")
@RestController
@Slf4j
public class HystrixController {


    @Autowired
    private HystrixService hystrixService;

   /* @HystrixCommand(commandKey = "/hystrix/test1",
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  				//设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),	//请求数达到后才计算
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "15000"), //休眠时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")	//错误率
        },
        threadPoolProperties = {
            @HystrixProperty(name=  "coreSize", value="10"),
            @HystrixProperty(name=  "maxQueueSize", value="20000"),
            @HystrixProperty(name=  "queueSizeRejectionThreshold", value="30")
        }  ,
        fallbackMethod = "testHystrix1Timeout"
    )*/
    @HystrixCommand(fallbackMethod = "testHystrix1Timeout")
    @GetMapping("/test1")
    public String testHystrix1(int number) throws Exception {
        if (number % 2 == 0) {
            if (number == 4) {
                throw  new RuntimeException();
            }else if (number==6) {
                throw new MyException("这是属于我的异常");
            }
            hystrixService.testHystrixService(number);
            Thread.sleep(1000);
            System.out.println("hystrix..."+"success");
            return "success";
        }
        Thread.sleep(5000);
        return "false";
    }

    public String testHystrix1Timeout(int number,Throwable e) {
        log.error(e.getMessage(),e);
        System.out.println("hystrix..."+number);
        return "太拥挤了, 请稍后再试~~";
    }
}
