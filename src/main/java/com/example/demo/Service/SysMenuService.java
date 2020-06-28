package com.example.demo.Service;

import com.example.demo.common.vo.Node;
import com.example.demo.common.vo.SysUserMenu;
import com.example.demo.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {

    List<SysUserMenu> findUserMenusByUserId(Integer id);

    /**
     * 修改
     * @param entity
     * @return
     */
    int updateObject(SysMenu entity);

    /**
     * 添加菜单数据结构
     * @param entity
     * @return
     */
    int saveObject(SysMenu entity);

    //添加中的树结构
    List<Node> findZtreeMenuNodes();

    /**
     * 删除自身信息
     * @param id
     * @return
     */
    int deleteObject(Integer id);
    /**
     * 查询
     * @return
     */
    List<Map<String,Object>> findObjects();
}
