package com.study.feign.hystrix;

import com.netflix.hystrix.Hystrix;
import com.study.feign.service.testFeign;
import org.springframework.stereotype.Component;

/**
 * fileName:TestHystrix
 * description:
 * author:hcq
 * createTime:2019-03-22 12:03
 */
/*@Component*/
public class TestHystrix implements testFeign {
    /*@Override
    public String consumer() {
        return "sorry 此服务出现了点问题";
    }*/
}
