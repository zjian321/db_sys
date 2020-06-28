package com.example.demo.dao;

import com.example.demo.common.vo.CheckBox;
import com.example.demo.common.vo.SysRoleMenuVo;
import com.example.demo.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleDao {

    /**
     * 用户添加页面显示角色信息
     * @return
     */
    @Select("select id,name from sys_roles")
    List<CheckBox> findObjects();

    /**
     * 更新数据实现
     * @param entity
     * @return
     */
    int updateObject(SysRole entity);

    /**
     * 添加页面呈现,基于角色id,显示菜单id,树结构
     * @param id
     * @return
     */
    SysRoleMenuVo fingdObjectById(Integer id);

    /**
     * 添加角色
     * @param entity
     * @return
     */
    int insertObject(SysRole entity);

    /**
     * 删除自身信息
     * @param id
     * @return
     */
    @Delete("delete from sys_roles where id=#{id}")
    int deleteObject(Integer id);

    /**
     * 查询总记录
     * @param name
     * @return
     */
    int getRowCount(@Param("name") String name);
    /**
     * 查询当前页记录
     * @param name
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SysRole> findPageObjects(String name,Integer startIndex,Integer pageSize);

}
