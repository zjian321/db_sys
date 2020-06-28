package com.example.demo.AopTests;

import com.example.demo.Service.SysUserService;
import com.example.demo.common.bo.PageObject;
import com.example.demo.common.vo.SysUserDeptVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testSysUserService {

    @Autowired
    private SysUserService userService;
    @Test
    public void testUserService(){
        PageObject<SysUserDeptVo> po=userService.findPageObjects("admin",1);
        System.out.println("rowCount="+po.getRowCount());
    }

}
