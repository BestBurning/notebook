package com.diyishuai.notebook.service;

import com.diyishuai.notebook.bean.User;
import com.diyishuai.notebook.dao.UserDao;
import com.diyishuai.notebook.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean login(User loginUser,HttpSession session){
        boolean result = false;
        if (loginUser!=null && loginUser.getEmail()!=null && loginUser.getPassword()!=null){
            User byEmail = userDao.findByEmail(loginUser.getEmail());
            if (byEmail !=null ){
                if (byEmail.getPassword().equals(loginUser.getPassword()))
                    LoginUtil.saveUserToSession(byEmail,session);
                    result = true;
            }
        }
        return result;
    }

}
