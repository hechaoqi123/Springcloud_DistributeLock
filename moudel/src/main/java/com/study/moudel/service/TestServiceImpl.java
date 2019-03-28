package com.study.moudel.service;

import org.springframework.stereotype.Service;

/**
 * fileName:TestServiceImpl
 * description:
 * author:hcq
 * createTime:2019-03-22 11:21
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String save() {
        return "This is moudel service save()";
    }
}
