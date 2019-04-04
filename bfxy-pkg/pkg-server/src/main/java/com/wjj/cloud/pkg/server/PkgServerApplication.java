package com.wjj.cloud.pkg.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@MapperScan("com.wjj.cloud.pkg.server.dao")
public class PkgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PkgServerApplication.class, args);
    }

}
