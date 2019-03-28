package com.study.moudel.controller;

import com.study.moudel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * fileName:hello
 * description:
 * author:hcq
 * createTime:2019-03-20 16:58
 */
//订单系统
@RestController
public class hello {
    public static int aaa=1;//统计生成的订单数
    @RequestMapping("/order_form")
    public String order_form() throws InterruptedException {

        return Thread.currentThread().getName()+"--成功生成订单---累计生成订单数"+aaa++;
    }
  /*  @RequestMapping("/hello")
    public String hello() {
        return service.save();
    }*/
}
