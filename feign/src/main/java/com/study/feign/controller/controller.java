package com.study.feign.controller;

import com.study.feign.redis.RedisLock;
import com.study.feign.service.OrderFeign;
import com.study.feign.service.RepositoryFeign;
import com.study.feign.service.testFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    /*@Autowired
    testFeign test;*/
    @Autowired
    OrderFeign order;//订单系统
    @Autowired
    RepositoryFeign repository;//库存系统
    @Autowired
    RedisLock lock;


   /* @RequestMapping("/hi")
    public String hi() {
        System.out.println("This is feign");
        return test.consumer();
    }*/
    //销售业务
    @RequestMapping("/shell")
    public String shell(){
        String token=lock.lockWithTimeout("repository",3000,60*1000);
        if(token!=null){
            try {
                if(repository.check()){
                    //产生订单
                    String result=order.order_form();
                    //开始出库
                    Integer num=repository.reduce(5);
                    return result+"剩余:"+num;
                }else{
                    System.out.println(Thread.currentThread().getName()+"剩余库存不足，出库失败");
                    return "剩余库存不足，出库失败";
                }
            }catch (Exception e){
                 System.out.println("出库异常"+e);
            }finally{
                  boolean result= lock.releaseLock("repository",token);//释放锁
                        System.out.println(Thread.currentThread().getName()+"锁释放："+result);
            }

        }

        return "Request Timeout";
    }

}
