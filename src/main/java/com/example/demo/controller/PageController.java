package com.example.demo.controller;

import com.example.demo.common.utils.ShiroUtils;
import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面返回层
 */
@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("doLogin")
    public String  doLogin(){
        return "login";
    }

    @RequestMapping("doIndexUI")
    public String doIndexUI(Model model) {
        SysUser user= ShiroUtils.getUser();
        model.addAttribute("user",user);
        return "starter";
    }

//    @RequestMapping("/log/log_list")
//    public String doLogUI() {
//        return "sys/log_list";
//    }

    @RequestMapping("doPageUI")
    public String doPageUI() {
        return "common/page";
    }

//    @RequestMapping("/menu/menu_list")
//    public String doMenuUI() {
//        return "sys/menu_list";
//    }

    @RequestMapping("/{module}/{moduleUI}")
    public String doModuleUI(@PathVariable String moduleUI) {
        return "sys/" + moduleUI;
    }


}
