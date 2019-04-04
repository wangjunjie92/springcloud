package com.wjj.cloud.payb.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@MapperScan("com.wjj.cloud.payb.server.dao")
public class PaybServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaybServerApplication.class, args);
    }

}
