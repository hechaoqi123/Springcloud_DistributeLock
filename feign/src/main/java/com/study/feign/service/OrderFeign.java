package com.study.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * fileName:OrderFeign
 * description:
 * author:hcq
 * createTime:2019-03-26 9:22
 */
@FeignClient(value = "login")
public interface OrderFeign {
    @RequestMapping("/order_form")
    String order_form();
}
