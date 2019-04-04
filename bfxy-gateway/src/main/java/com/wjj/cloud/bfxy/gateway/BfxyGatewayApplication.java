package com.wjj.cloud.bfxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class BfxyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfxyGatewayApplication.class, args);
    }

}
