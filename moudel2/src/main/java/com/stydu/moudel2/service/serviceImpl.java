package com.stydu.moudel2.service;


import com.stydu.moudel2.controller.test;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * fileName:serviceImpl
 * description:
 * author:hcq
 * createTime:2019-03-22 11:25
 */
@Service
public class serviceImpl implements TestService {
    @Override
    public Integer sell(int num) throws Exception {
        //持久化操作  模拟出库  主要用来验证事务的回滚
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/first","root","root");
        conn.setAutoCommit(true);
        PreparedStatement pre = conn.prepareStatement("insert into users values("+(200+test.repositoryNum)+",'hehe','repository','orde')");
        boolean a=pre.execute();
        System.out.println(a);
        pre.close();
        conn.close();

        long curr=System.currentTimeMillis();
            test.repositoryNum-=num;
            test.shellNum+=num;
            System.out.println("耗时:"+(System.currentTimeMillis()-curr)+Thread.currentThread().getName()+"成功卖出="+num+"共卖出"+test.shellNum+",剩余库存="+test.repositoryNum);
         return  test.repositoryNum;
    }

}
