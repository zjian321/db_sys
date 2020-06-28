package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleDao {

    /**
     * 修改数据,先删除之前的元数据
     * @param userId
     * @return
     */
    int deleteObjectsByUserId(Integer userId);

    /**
     * 根据用户id查找到对应的角色id
     * @param id
     * @return
     */
    List<Integer> findRoleIdByUserId(Integer id);



    /**
     * 基于用户id,添加对应的多个角色id
     * @param userId
     * @param roleId
     * @return
     */
    int insertObjects(Integer userId,Integer[] roleIds);

    /**
     * 基于角色删除用户对应的数据
     * @param roleId
     * @return
     */
    @Delete("delete from sys_user_roles where role_id=#{roleId}")
    int deleteObjectsByRoleId(Integer roleId);
}
