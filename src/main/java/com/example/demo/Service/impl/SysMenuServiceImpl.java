package com.example.demo.Service.impl;

import com.example.demo.Service.SysMenuService;
import com.example.demo.common.exception.ServiceException;
import com.example.demo.common.vo.Node;
import com.example.demo.common.vo.SysUserMenu;
import com.example.demo.dao.SysMenuDao;
import com.example.demo.dao.SysRoleMenuDao;
import com.example.demo.dao.SysUserRoleDao;
import com.example.demo.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao SysMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuDao SysRoleMenuDao;

    /**
     * 菜单动态显示
     * @param id
     * @return
     */
    @Override
    public List<SysUserMenu> findUserMenusByUserId(Integer id) {
        //1.基于用户id获取角色id
        List<Integer> roleIds = sysUserRoleDao.findRoleIdByUserId(id);
        // 2.基于角色id获取菜单id
        List<Integer> menuIds = SysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
        //3.基于菜单获取一级菜单和二级菜单信心
        return SysMenuDao.findMenusByIds(menuIds);
    }

    //修改
    @CacheEvict(value = "menuCache",allEntries=true)
    @Override
    public int updateObject(SysMenu entity) {
        //1.参数验证  entity 和菜单名
        if (entity==null)
            throw new ServiceException("信息不能为空");
        //2.菜单名不能为空
        if (StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");
        //3.更新数据
        int rows = SysMenuDao.updateObject(entity);
        return rows;
    }

    //添加数据
    @CacheEvict(value = "menuCache",allEntries=true)
    @Override
    public int saveObject(SysMenu entity) {
        //1.参数验证  entity 和菜单名
        if (entity==null)
            throw new ServiceException("信息不能为空");
        //2.菜单名不能为空
        if (StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");
        //3.添加数据
        int rows = SysMenuDao.insertObject(entity);
        if (rows==0)
            throw new ServiceException("保存失败");
        return rows;
    }

    //添加中的树结构
    @Override
    public List<Node> findZtreeMenuNodes() {
        return SysMenuDao.findZtreeMenuNodes();
    }

    //删除
    @CacheEvict(value = "menuCache",allEntries=true)
    @Override
    public int deleteObject(Integer id) {
       //1.参数校验.id
        if (id==null || id<=0)
            throw new IllegalArgumentException("id值不合法");
        //2.查看是否有子元素,校验
        int childCount = SysMenuDao.getChildCount(id);
        if (childCount>0)
            throw new ServiceException("请先删除子菜单");
        //3.删除关系数据
        SysMenuDao.deleteObjectsByMenuId(id);
        //4.删除自身信息 校验
        int rows = SysMenuDao.deleteObject(id);
        if (rows==0)
            throw new ServiceException("记录可能已经不存在");
        return rows;
    }

    //查询
    //将查询结果进行cache
    //spring中的cache:Map<String,cache>
    @Cacheable(value = "menuCache")//具体cacche操作由AOP实现(@Cacheable描述的方法)
    @Override
    public List<Map<String, Object>> findObjects() {
        System.out.println("菜单查询---SysMenuServiceImpl.findObjects");
        List<Map<String, Object>> list = SysMenuDao.findObjects();
        if (list==null || list.size()==0)
            throw new ServiceException("没有对应的菜单信息");
        return list;
    }
}
