package com.study.moudel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
    public void order_form() throws Exception {
        //持久化操作  模拟出库  主要用来验证事务的回滚
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/first","root","root");
        conn.setAutoCommit(true);
        PreparedStatement pre = conn.prepareStatement("insert into users values("+(100+aaa)+",'hehe','order','orde')");
        boolean a=pre.execute();
        System.out.println(a);
        pre.close();
        conn.close();
        System.out.println(Thread.currentThread().getName()+"--成功生成订单---累计生成订单数"+aaa++);
    }
}
