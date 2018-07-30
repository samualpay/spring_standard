package com.samual.standard.controller;

import com.samual.standard.bean.HelloBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Samual on 2018/7/26.
 */
@RestController
public class MyRestController {
    @GetMapping("/rest")
    public HelloBean rest(){
        HelloBean helloBean = new HelloBean();
        helloBean.setTitle("Hello World");
        helloBean.setContent("welcom to my first Rest api");
        return helloBean;
    }
}
