package com.samual.standard.controller;

import com.samual.standard.Entity.User;
import com.samual.standard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Samual on 2018/7/27.
 */
@Controller
public class CRUDController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/user")
    public String showUsers(ModelMap modelMap){
        modelMap.put("list",userRepository.findAll());
        return "crud";
    }
    @GetMapping("/user/modify")
    public String modifyUserView(Long id,ModelMap modelMap){
        modelMap.put("item",userRepository.findById(id).get());
        return "crud_modify";
    }
    @PostMapping("/user")
    public String addUser(String name,Integer age){
        User user =new User(name,age);
        userRepository.save(user);
        return "redirect:/user";
    }
    @PostMapping("/user/delete")
    public String deleteUser(Long id){
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return "redirect:/user";
    }
    @PostMapping("/user/modify")
    public String updateUser(User user){
        User userInDB = userRepository.findById(user.getId()).get();
        userInDB.setName(user.getName());
        userInDB.setAge(user.getAge());
        userRepository.saveAndFlush(userInDB);
        return "redirect:/user";
    }
}
