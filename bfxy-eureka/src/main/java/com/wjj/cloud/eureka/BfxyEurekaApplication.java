package com.wjj.cloud.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaServer
public class BfxyEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfxyEurekaApplication.class, args);

    }

    @RestController
    @Slf4j
    static class TestController {

        @GetMapping("/test")
        public String test(int pid) {
            log.info("测试日志框架:{}",pid);
            return "success";
        }

        @GetMapping("/test2")
        public String test2(int pid) {
            if (pid==2) {
                throw new RuntimeException();
            }else if (pid % 2!=0) {
                int i=10/0;
            }
            return "success";
        }
    }

}

