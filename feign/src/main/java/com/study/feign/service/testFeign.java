package com.study.feign.service;

import com.study.feign.hystrix.TestHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value = "login", fallback = TestHystrix.class)

public interface testFeign {
   @RequestMapping("/hello")
    String consumer();
}
