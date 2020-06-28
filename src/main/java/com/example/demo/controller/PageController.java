package com.example.demo.controller;

import com.example.demo.Service.SysMenuService;
import com.example.demo.common.utils.ShiroUtils;
import com.example.demo.common.vo.SysUserMenu;
import com.example.demo.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 页面返回层
 */
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private SysMenuService SysMenuService;
    @RequestMapping("doLogin")
    public String  doLogin(){
        return "login";
    }

    @RequestMapping("doIndexUI")
    public String doIndexUI(Model model) {
        //获取登录用户
        SysUser user= ShiroUtils.getUser();
        model.addAttribute("user",user);
        //查询用户对应的菜单信息(一.二级)并储存到model,然就在也买你上进行呈现
        List<SysUserMenu> userMenus = SysMenuService.findUserMenusByUserId(user.getId());
        model.addAttribute("userMenus",userMenus);
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
