package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuDao {

    /**
     * 基于角色id,查找多个菜单id
     * @param id
     * @return
     */
    List<Integer> findMenuIdsByRoleId(Integer id);
    /**
     * 基于角色id添加对应的菜单id
     * @return
     */
    int insertObjects(Integer roleId,Integer[] menuIds);

    /**
     * 基于多个角色id查询队对应的菜单id
     * @param id
     * @return
     */
    List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);

    /**
     * 基于角色id删除对应菜单信息
     * @param
     * @return
     */
    @Delete("delete from sys_role_menus where role_id=#{roleId}")
    int deleteObjectsByRoleId(Integer roleId);

}
