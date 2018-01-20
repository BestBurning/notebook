package com.diyishuai.notebook.controller;

import com.diyishuai.notebook.bean.NoteBook;
import com.diyishuai.notebook.bean.User;
import com.diyishuai.notebook.constants.Constants;
import com.diyishuai.notebook.service.NoteBookService;
import com.diyishuai.notebook.utils.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notebook")
public class NoteBookController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoteBookService noteBookService;

    @ResponseBody
    @RequestMapping("getAll")
    public Map getAllNoteBook(HttpSession session){
        Map result = new HashMap();
        //从session中获取用户ID
        User user = LoginUtil.getUserFromSession(session);
        List<NoteBook> noteBooks = null;
        try {
            noteBooks = noteBookService.getAllByUserId(user.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("allNoteBook",noteBooks);
        return result;
    }


    @ResponseBody
    @RequestMapping("add")
    public Map addNoteBook(NoteBook noteBook,HttpSession session) {
        Map result = new HashMap();

        User user = LoginUtil.getUserFromSession(session);
        noteBook.setRowKey(user.getId().toString());
        long currentTimeMillis = System.currentTimeMillis();
        noteBook.setCreateTime(String.valueOf(currentTimeMillis));
        noteBook.setStatus("0");
        boolean success = noteBookService.save(noteBook);
        result.put("success",success);
        if (success){
            result.put("resource", user.getId() + Constants.ROWKEY_SEPARATOR + currentTimeMillis);
        }
        return result;
    }

}
