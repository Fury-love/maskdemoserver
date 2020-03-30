package com.mask.maskdemoserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class MaskdemoserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaskdemoserverApplication.class, args);
    }

}
