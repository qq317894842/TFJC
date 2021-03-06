package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/19 14:57
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.dao")
@PropertySource({"classpath:client.properties"})
public class ApiServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }
}
