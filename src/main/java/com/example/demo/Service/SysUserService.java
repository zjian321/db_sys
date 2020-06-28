package com.example.demo.Service;

import com.example.demo.common.bo.PageObject;
import com.example.demo.common.vo.SysUserDeptVo;
import com.example.demo.entity.SysUser;

import java.util.Map;

public interface SysUserService {

    int updatePassword(String sourcePassword,String newPassword,String cfgPassword);

    /**
     * 修改对应的信息,和角色
     * @param entity
     * @param roleIds
     * @return
     */
    int updateObject(SysUser entity,Integer[] roleIds);

    /**
     * 修改页面回显
     * @param userId
     * @return
     */
    Map<String,Object> findObjectById(Integer userId);
    /**
     * 添加用户信息
     * @param entity
     * @param roleIds
     * @return
     */
    int saveObject(SysUser entity,Integer[] roleIds);

    /**
     * 设置状态
     * @param id
     * @param valid
     * @return
     */
    int validById(Integer id,Integer valid);

    /**
     * 查询
     * @param username
     * @param pageCurrent
     * @return
     */
    PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);
}
