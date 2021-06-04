package com.project.data_cloud_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.data_cloud_server.mapper")
public class DataCloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCloudServerApplication.class, args);
    }

}
