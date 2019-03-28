package com.stydu.moudel2.service;


import com.stydu.moudel2.controller.test;
import org.springframework.stereotype.Service;

/**
 * fileName:serviceImpl
 * description:
 * author:hcq
 * createTime:2019-03-22 11:25
 */
@Service
public class serviceImpl implements TestService {
    @Override
    public Integer sell(int num) {
        long curr=System.currentTimeMillis();
            test.repositoryNum-=num;
            test.shellNum+=num;
            System.out.println("耗时:"+(System.currentTimeMillis()-curr)+Thread.currentThread().getName()+"成功卖出="+num+"共卖出"+test.shellNum+",剩余库存="+test.repositoryNum);
         return  test.repositoryNum;
    }

}
