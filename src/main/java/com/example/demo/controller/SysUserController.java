package com.example.demo.controller;

import com.example.demo.Service.SysUserService;
import com.example.demo.common.vo.JsonResult;
import com.example.demo.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    //修改密码
    @RequestMapping("doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd){
        sysUserService.updatePassword(pwd,newPwd,cfgPwd);
        return new JsonResult("update ok!!!");
    }

    @RequestMapping("/doLogin")
    public JsonResult doLogin(String username,String password,boolean isRememberMe){
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
       //设置记住我的功能
        token.setRememberMe(isRememberMe);

        Subject currentUser= SecurityUtils.getSubject();
        currentUser.login(token);//将用户信息提交给securitymanger进行认证
        return new JsonResult("login ok!!!");
    }


    //修改
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds){
        sysUserService.updateObject(entity,roleIds);
        return new JsonResult("update ok!!!");
    }
    //修改页面
    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysUserService.findObjectById(id));
    }
    //添加
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysUser entity,Integer[] roleIds){
        sysUserService.saveObject(entity,roleIds);
        return new JsonResult("save ok!!!");
    }
    //分页查询
    @RequestMapping("doValidById")
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.validById(id,valid);
        return new JsonResult("update ok!!!");
    }
    //分页查询
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        return new JsonResult(sysUserService.findPageObjects(username,pageCurrent));
    }
}
