package com.diyishuai.notebook.controller;

import com.diyishuai.notebook.bean.User;
import com.diyishuai.notebook.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "save")
    public String  saveUser(User user){
        User byEmail = userDao.findByEmail(user.getEmail());
        if (byEmail==null){
            userDao.save(user);
            return "redirect:/login.html";
        }else {
            return "redirect:/register.html";
        }

    }

}
