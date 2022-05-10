package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MyConfig {
//    @Bean
//    WebMvcConfigurer createWebMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            /**
//             * 使用CorsRegistry
//             * 第二种方法是在WebMvcConfigurer中定义一个全局CORS配置，下面是一个示例：
//             * 这种方式可以创建一个全局CORS配置，如果仔细地设计URL结构，
//             * 那么可以一目了然地看到各个URL的CORS规则，推荐使用这种方式配置CORS。
//             * @param registry
//             */
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/user/**")
//                        .allowedOrigins("http://localhost:8080")
//                        .allowedMethods("GET", "POST")
//                        .maxAge(3600);
//                // 可以继续添加其他URL规则:
//                // registry.addMapping("/rest/v2/**")...
//            }
//        };
//    }
}
