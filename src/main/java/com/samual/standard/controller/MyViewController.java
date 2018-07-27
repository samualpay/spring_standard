package com.samual.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Samual on 2018/7/26.
 */
@Controller
public class MyViewController {
    @GetMapping("/greeting/{age}")
    public String greeting(@RequestParam(value="name", required=false, defaultValue = "World") String name,ModelMap modelMap,@PathVariable Integer age){
        modelMap.put("name",name);
        modelMap.put("age" , age);
        return "greeting";
    }

}
