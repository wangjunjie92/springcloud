package com.wjj.cloud.paya.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@MapperScan("com.wjj.cloud.paya.server.dao")
public class PayaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayaServerApplication.class, args);
    }

}
