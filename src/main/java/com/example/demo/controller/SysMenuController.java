package com.example.demo.controller;

import com.example.demo.Service.SysMenuService;
import com.example.demo.common.vo.JsonResult;
import com.example.demo.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu/")
public class SysMenuController {

    @Autowired
    private SysMenuService SysMenuService;



    //修改数据
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysMenu entity){
        SysMenuService.updateObject(entity);
        return new JsonResult("update ok!!!");
    }
    //添加数据
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysMenu entity){
        SysMenuService.saveObject(entity);
        return new JsonResult("save ok!!!");
    }
    //添加页面树结构
    @RequestMapping("doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNodes(){
        return new JsonResult(SysMenuService.findZtreeMenuNodes());
    }
    //删除
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        SysMenuService.deleteObject(id);
        return new JsonResult("delete ok!!!");
    }

    //查询
    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects(){
        return new JsonResult(SysMenuService.findObjects());
    }


}
