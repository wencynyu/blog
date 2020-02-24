package com.yuwenxin.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yuwenxin.blog.dao")
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BlogApplication.class, args);
        SpringApplication app = new SpringApplication(BlogApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
