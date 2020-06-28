package com.example.demo.controller;

import com.example.demo.Service.SysLogService;
import com.example.demo.common.bo.PageObject;
import com.example.demo.common.vo.JsonResult;
import com.example.demo.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log/")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;
   //查询
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        PageObject<SysLog> pageObjects = sysLogService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObjects);
    }
   //删除
    @RequestMapping("doDeleteObjects")
    public JsonResult doDeleteObjects(Integer...ids){
        sysLogService.deleteObjects(ids);
        return new JsonResult("delete ok!");
    }
}
