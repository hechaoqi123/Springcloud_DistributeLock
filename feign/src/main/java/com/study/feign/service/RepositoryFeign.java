package com.study.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fileName:RepositoryFeign
 * description:
 * author:hcq
 * createTime:2019-03-26 9:28
 */
@FeignClient(value = "logout")
public interface RepositoryFeign {
    @RequestMapping("/reduce")
    Integer reduce(@RequestParam("num") int num);
    @RequestMapping("/check_num")
    boolean check();
}
