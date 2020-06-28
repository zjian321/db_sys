package com.example.demo.controller;

import com.example.demo.Service.SysRoleService;
import com.example.demo.common.vo.JsonResult;
import com.example.demo.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //用户添加页面角色显示
    @RequestMapping("doFindRoles")
    public JsonResult doFindRoles(){
        return new JsonResult(sysRoleService.findObjects());
    }
    //更新数据显示
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity,Integer[] menuIds){
        sysRoleService.updateObject(entity,menuIds);
        return new JsonResult("update ok!!1");
    }
    //修改显示
    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysRoleService.fingdObjectById(id));
    }
    //添加
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole entity,Integer[] menuIds){
        sysRoleService.saveObject(entity,menuIds);
        return new JsonResult("save ok!!!");
    }
    //删除
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        sysRoleService.deleteObject(id);
        return new JsonResult("delete ok!!!");
    }
    //查询
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String name,Integer pageCurrent){
        return new JsonResult(sysRoleService.findPageObjects(name,pageCurrent));
    }

}
