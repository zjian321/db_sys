package com.example.demo.common.utils;

import com.example.demo.entity.SysUser;
import org.apache.shiro.SecurityUtils;


public class ShiroUtils {
    public static String getUsername(){
        return getUser().getUsername();
    }
    public static SysUser getUser(){
        return (SysUser)SecurityUtils.getSubject().getPrincipal();
    }

}
