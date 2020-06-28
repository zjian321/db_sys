package com.example.demo.Service.impl;

import com.example.demo.Service.SysRoleService;
import com.example.demo.common.bo.PageObject;
import com.example.demo.common.exception.ServiceException;
import com.example.demo.common.vo.CheckBox;
import com.example.demo.common.vo.SysRoleMenuVo;
import com.example.demo.dao.SysRoleDao;
import com.example.demo.dao.SysRoleMenuDao;
import com.example.demo.dao.SysUserRoleDao;
import com.example.demo.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao SysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    //用户添加页面角色信息显示
    @Override
    public List<CheckBox> findObjects() {
        return SysRoleDao.findObjects();
    }

    //更新数据实现
    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
        //参数校验entity, 名称,id,menuIds,
        if (entity==null)
            throw new IllegalArgumentException("更新对象不能为空");
        if (entity.getId()==null)
            throw new IllegalArgumentException("id不能为空");
        if (StringUtils.isEmpty(entity.getName()))
            throw new IllegalArgumentException("名称不能为空");
        if(menuIds==null || menuIds.length==0)
            throw new IllegalArgumentException("必须要指定一个角色");
        //2.获取更新数据
        int rows=SysRoleDao.updateObject(entity);
        if (rows==0)
            throw new ServiceException("对象可能已经不存在");
        //3.删除之前原有的角色菜单数据
        sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
        //4.在插入新的角色菜单数据
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
            return rows;
    }

    //修改页面呈现 树结构
    @Override
    public SysRoleMenuVo fingdObjectById(Integer id) {
       //参数校验
        if (id==null || id<=0)
            throw new IllegalArgumentException("id值不合法");
        //2.查询所有id
        SysRoleMenuVo result=SysRoleDao.fingdObjectById(id);
        if (result==null)
            throw new ServiceException("此纪录可能已经不存在");
        return result;
    }

    //添加
    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
       //参数校验 entity,menuIds,entuty.getNmae()角色名
        if (entity==null)
            throw new IllegalArgumentException("保存信息不能为空");
        if (StringUtils.isEmpty(entity.getName()))
            throw new IllegalArgumentException("角色名不能为空");
        if (menuIds==null || menuIds.length==0)
            throw new IllegalArgumentException("必须为角色分配权限");
        //2.添加自身信息
        int rows=SysRoleDao.insertObject(entity);
        //3.添加菜单关系数据
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
        return rows;
    }

    //删除
    @Override
    public int deleteObject(Integer id) {
        //参数校验 id
        if (id==null || id<=0)
            throw new IllegalArgumentException("请先选择要删除的数据");
        //删除关系数据
        sysRoleMenuDao.deleteObjectsByRoleId(id);
        sysUserRoleDao.deleteObjectsByRoleId(id);
        //删除自身信息
        int rows=SysRoleDao.deleteObject(id);
        if (rows==0)
            throw new ServiceException("此纪录可能已经不存在");
        return rows;
    }

    @Override
    public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
        //参数校验
        if (pageCurrent==null || pageCurrent<1)
            throw new IllegalArgumentException("当前页码值无效");
        int rowCount = SysRoleDao.getRowCount(name);
        if (rowCount==0)
            throw new IllegalArgumentException("没有找到对应的记录");
        int pageSize=3;
        int startIndex=(pageCurrent-1)*pageSize;
        List<SysRole> records = SysRoleDao.findPageObjects(name, startIndex, pageSize);
        //对结果进行封装并返回
        return new PageObject<>(pageCurrent,pageSize,rowCount,records);
    }
}
