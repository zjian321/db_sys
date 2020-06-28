package com.example.demo.dao;
//菜单

import com.example.demo.common.vo.Node;
import com.example.demo.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuDao {


    /**
     * 基于菜单id获取授权标识对象
     * @param menuIds
     * @return
     */
    List<String> findPermissions(Integer[] menuIds);




    int updateObject(SysMenu entity);

    /**
     * 菜单数据添加
     * @param entity
     * @return
     */
    int insertObject(SysMenu entity);

    /**
     *获取父类菜单信息---添加中的树结构
     * @return
     */
    @Select("select id,name,parentId from sys_menus")
    List<Node> findZtreeMenuNodes();

    /**
     * 查询
     * @return
     */
    List<Map<String,Object>> findObjects();

    /**
     * 删除
     * @param id
     * @return------先查看是否有子菜单,有则无法删除
     */
    //删除角色菜单关系数据
    int deleteObjectsByMenuId(Integer menuId);
    //获取子类菜单
    int getChildCount(Integer id);
    //删除自身信息
    int deleteObject(Integer id);

}
