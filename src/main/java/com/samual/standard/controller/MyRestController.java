package com.samual.standard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Samual on 2018/7/26.
 */
@RestController
public class MyRestController {
    @GetMapping("/rest")
    public String rest(){
        return "Hello,My First Rest";
    }
}
