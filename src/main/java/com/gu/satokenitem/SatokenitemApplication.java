package com.gu.satokenitem;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@MapperScan("com.gu.satokenitem.mapper")
@SpringBootApplication
public class SatokenitemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatokenitemApplication.class, args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }
    @Bean
    RestTemplate midTemplate() {
        return new RestTemplate();
    }

}
