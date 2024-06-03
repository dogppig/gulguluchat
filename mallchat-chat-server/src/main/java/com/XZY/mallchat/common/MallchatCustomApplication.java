package com.XZY.mallchat.common;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.XZY.mallchat"})
@MapperScan({"com.XZY.mallchat.common.**.mapper","com.XZY.mallchat.common.user.config"})
public class MallchatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallchatCustomApplication.class);
    }
}
