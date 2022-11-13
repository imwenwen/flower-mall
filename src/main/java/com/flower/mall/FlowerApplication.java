
package com.flower.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.flower.mall.dao")
@SpringBootApplication
public class FlowerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowerApplication.class, args);
    }
}
