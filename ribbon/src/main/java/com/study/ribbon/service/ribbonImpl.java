package com.study.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * fileName:ribbonImpl
 * description:
 * author:hcq
 * createTime:2019-03-22 11:36
 */
@Service
public class ribbonImpl implements ribbon {
    @Autowired
    RestTemplate rest;

    @Override
    public String ribbon() {
        System.out.println("hello This is Ribbon()");
        return rest.getForObject("http://login/hello", String.class);
    }
}
