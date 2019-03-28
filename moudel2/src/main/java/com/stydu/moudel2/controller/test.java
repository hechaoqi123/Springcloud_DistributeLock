package com.stydu.moudel2.controller;

import com.stydu.moudel2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * fileName:test
 * description:
 * author:hcq
 * createTime:2019-03-20 17:12
 */
//库存系统
@RestController
public class test {
   public static Integer repositoryNum=100;//初始库存
   public static Integer shellNum=0;//剩余库存数
   //检查库存是否充足
    @RequestMapping("/check_num")
    public boolean check_num(){
        return repositoryNum>=5;
    }
   //出库
   @RequestMapping("/reduce")
    public Integer reduce(int num){
        return service.sell(num);
    }

    @Autowired
    TestService service;
    @RequestMapping("/hello")
    public String hello() {
        return service.delete();
    }
}
