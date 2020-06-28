package com.example.demo.dao;

import com.example.demo.common.vo.SysUserDeptVo;
import com.example.demo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserDao {
    /**
     * 修改密码
     * @param username
     * @param password
     * @param salt
     * @return
     */
    @Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where username=#{username}")
    int updatePassword(String username,String password,String salt);

    /**
     * 用户信息认证shiro
     * @param username
     * @return
     */
    @Select("select * from sys_users where username=#{username}")
    SysUser findUserByUserName(String username);

    /**
     * 修改
     * @param entity
     * @return
     */
    int updateObject(SysUser entity);

    /**
     * 根据用户id修改页面数据回显
     * @param id
     * @return
     */
    SysUserDeptVo findObjectById(Integer id);

    /**
     * 添加
     * @param entity
     * @return
     */
    int insertObject(SysUser entity);

    /**
     * 设置禁用
     * @param id
     * @param valid
     * @param modifiedUser
     * @return
     */
    int validById(Integer id,Integer valid,String modifiedUser);

    /**
     * 查询用户总记录数
     * @param username
     * @return
     */
    int getRowCount(String username);

    /**
     * 分页查询
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SysUserDeptVo> findPageObjects(String username,
                                Integer startIndex,Integer pageSize);
}
