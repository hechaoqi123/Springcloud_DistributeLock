package com.study.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * fileName:controller
 * description:
 * author:hcq
 * createTime:2019-03-22 11:30
 */
@RestController
public class controller {
    @Autowired
    com.study.ribbon.service.ribbon ribbon;

    @RequestMapping("/ribbon/{param}")
    public String ribbon(@PathVariable String param) {
        System.out.println("parameter==" + param);
        return ribbon.ribbon();
    }
}
