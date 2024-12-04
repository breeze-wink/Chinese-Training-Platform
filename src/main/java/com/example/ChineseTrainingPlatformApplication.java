package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.example.mapper")  // 确保指定的包路径包含了您的Mapper接口
@EnableAsync
public class ChineseTrainingPlatformApplication {
//
    public static void main(String[] args) {
        SpringApplication.run(ChineseTrainingPlatformApplication.class, args);
    }

}
