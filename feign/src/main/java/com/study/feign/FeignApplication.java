package com.study.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients   //声明这个是一个消费者
@EnableDiscoveryClient  //开启服务发现   与@EnableEurekaClient作用类似，后者比较单一
@SpringBootApplication   //消费者  对外开放的接口
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
