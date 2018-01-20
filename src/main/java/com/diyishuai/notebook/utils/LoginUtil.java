package com.diyishuai.notebook.utils;

import com.diyishuai.notebook.bean.User;

import javax.servlet.http.HttpSession;

public class LoginUtil {

    private static final String LOGIN_USER = "loginUser";

    public static void saveUserToSession(User user, HttpSession session){
        session.setAttribute(LOGIN_USER,user);
    }

    public static User getUserFromSession(HttpSession session){
        return (User) session.getAttribute(LOGIN_USER);
    }

}
