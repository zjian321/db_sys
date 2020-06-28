package com.example.demo.Service;

import com.example.demo.common.bo.PageObject;
import com.example.demo.common.vo.CheckBox;
import com.example.demo.common.vo.SysRoleMenuVo;
import com.example.demo.entity.SysRole;

import java.util.List;

public interface SysRoleService {

    /**
     * 用户添加页面角色信息显示
     * @return
     */
    List<CheckBox> findObjects();

    /**
     * 更新数据
     * @param entity
     * @return
     */
    int updateObject(SysRole entity,Integer[] menuIds);

    /**
     * 修改页面显示
     * @param id
     * @return
     */
    SysRoleMenuVo fingdObjectById(Integer id);

    /**
     * 添加
     * @param entity
     * @param menuIds
     * @return
     */
    int saveObject(SysRole entity,Integer[] menuIds);
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteObject(Integer id);

    /**
     * 查询角色信息
     * @param name
     * @param pageCurrent
     * @return
     */
    PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);
}
