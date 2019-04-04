package com.wjj.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class BfxyEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfxyEurekaApplication.class, args);

    }

}

