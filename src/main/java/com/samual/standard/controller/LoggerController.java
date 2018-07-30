package com.samual.standard.controller;

import com.samual.standard.bean.HelloBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Samual on 2018/7/30.
 */
@RestController
public class LoggerController {
    private static final Logger logger = LoggerFactory.getLogger(LoggerController.class);
    @GetMapping("/log")
    public HelloBean log()throws Exception{
        logger.debug("Test debug");
        logger.info("Test info");
        logger.warn("Test warn");
        logger.error("Test error");
        HelloBean helloBean = new HelloBean();
        helloBean.setTitle("Log");
        helloBean.setContent("welcom to my first log api");
        return helloBean;
    }
}
