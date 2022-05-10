package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class VueBackgroundSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueBackgroundSystemApplication.class, args);
    }

}
