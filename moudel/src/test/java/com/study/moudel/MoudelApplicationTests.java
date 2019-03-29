package com.study.moudel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoudelApplicationTests {

    @Test
    public void contextLoads() throws Exception {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/first","root","root");
            conn.setAutoCommit(true);
            PreparedStatement pre = conn.prepareStatement("insert into users values(100,'hehe','order','orde')");
            boolean a=pre.execute();
            System.out.println(a);
            pre.close();
            conn.close();
    }

}
