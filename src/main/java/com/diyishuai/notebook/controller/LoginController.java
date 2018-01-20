package com.diyishuai.notebook.controller;

import com.diyishuai.notebook.bean.User;
import com.diyishuai.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @RequestMapping(path = "main")
    public String login(User user, HttpSession session){
        boolean islogin = userService.login(user, session);
        if (islogin){
            return "/notecenter";
        }else {
            return "redirect:/login.html";
        }
    }

}
