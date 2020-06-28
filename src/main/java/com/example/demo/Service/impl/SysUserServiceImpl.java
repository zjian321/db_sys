package com.example.demo.Service.impl;

import com.example.demo.Service.SysUserService;
import com.example.demo.common.annotation.RequiredLog;
import com.example.demo.common.bo.PageObject;
import com.example.demo.common.exception.ServiceException;
import com.example.demo.common.utils.ShiroUtils;
import com.example.demo.common.vo.SysUserDeptVo;
import com.example.demo.dao.SysUserDao;
import com.example.demo.dao.SysUserRoleDao;
import com.example.demo.entity.SysUser;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
//事务可以描述类,方法
@Transactional(timeout = 60,//设置超时
                isolation = Isolation.READ_COMMITTED,//隔离级别
                readOnly = false,//是否加锁,更新的时候,true只读.
                rollbackFor = Throwable.class,//异常回滚
                propagation = Propagation.REQUIRED//传播特性(当A事务调用B事务时,B事务放弃,执行A)
                )
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    //修改密码
    @Override
    public int updatePassword(String sourcePassword, String newPassword, String cfgPassword) {
      //1.参数校验
        if (StringUtils.isEmpty(sourcePassword))
            throw new IllegalArgumentException("原密码不能空");
        if (StringUtils.isEmpty(newPassword))
            throw new IllegalArgumentException("新密码不能空");
        if (!newPassword.equals(cfgPassword))
            throw new IllegalArgumentException("两次新密码输入不一致");
        //校验输入的原密码是否正确
        SysUser user=ShiroUtils.getUser();
        String sourceHashedPassword=user.getPassword();
        SimpleHash sh=new SimpleHash("MD5",sourcePassword,user.getSalt(),1);
        String hashedInputPassword=sh.toHex();
        if (!sourceHashedPassword.equals(hashedInputPassword))
            throw new IllegalArgumentException("原密码输入不正确");
      //2.更新密码
        String newSalt=UUID.randomUUID().toString();
        sh=new SimpleHash("MD5",newPassword,newSalt,1);
        String newHashedPassword=sh.toHex();
        int rows=sysUserDao.updatePassword(user.getUsername(),newHashedPassword,newSalt);
        if (rows==0)
            throw new IllegalArgumentException("账号已经不存在");
        return rows;
    }

    //修改
    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
        //1.参数校验 entity ,用户名,roleIds,
        if (entity==null)
            throw new IllegalArgumentException("信息不能空");
        if (StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能空");
        if (roleIds==null || roleIds.length==0)
            throw new IllegalArgumentException("至少分配一个角色");
        //2.执行,获取自身信息
        int rows=sysUserDao.updateObject(entity);
        //3.获取角色信息先删除原数据
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(),roleIds);
        return rows;
    }

    //修改页面回显
    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        //1.参数校验 userId
        if (userId==null || userId<=0)
            throw new IllegalArgumentException("参数不合法");
        //2.用户信息
        SysUserDeptVo user=sysUserDao.findObjectById(userId);
        if (user==null)
            throw new ServiceException("此用户可能已经不存在");
        //3.显示对应的角色信息
        List<Integer> roleIds=sysUserRoleDao.findRoleIdByUserId(userId);
        Map<String,Object> map=new HashedMap();
        map.put("user",user);
        map.put("roleIds",roleIds);
        return map;
    }
    //添加用户信息
    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //参数校验entity,用户名,密码,roleIds,
        if(entity==null)
            throw new IllegalArgumentException("信息不能为空");
        if (StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if (StringUtils.isEmpty(entity.getPassword()))
            throw new IllegalArgumentException("密码不能为空");
        if (roleIds==null || roleIds.length==0)
            throw new IllegalArgumentException("至少分配一个角色");
        //2.保存密码,并且加密
        String source=entity.getPassword();
        String salt=UUID.randomUUID().toString();
        SimpleHash hs=new SimpleHash("MD5",source,salt,1);
        entity.setSalt(salt);
        entity.setPassword(hs.toHex());
        //3.执行,添加自身
        int rows=sysUserDao.insertObject(entity);
        //4.保存角色信息
        sysUserRoleDao.insertObjects(entity.getId(),roleIds);
        return rows;
    }

    /**
     * 设置启用操作
     * spring框架通过@RequiresPermissions注解定义切入点,由注解描述的方法
     * 表示要进行授权菜单访问.那什么情况下会进行授权?在执行方法时要基于登录用户获取用户的
     * 权限标识("sys:user:update")
     * 然后判断用户拥有的这些权限表示是否包含@RequiresPermissions注解中定义的权限标识,加入包含则
     * 授权访问
     * @param id
     * @param valid
     * @return
     */
    //设置禁用状态
    //@Override
    @RequiresPermissions("sys:user:update")
    public int validById(Integer id, Integer valid) {
        //参数验证id,valid
        if (id==null || id<=0)
            throw new IllegalArgumentException("请先选择");
        if (valid!=0 && valid!=1)
            throw new IllegalArgumentException("参数不合法");
        //2.执行操作
        int rows=sysUserDao.validById(id,valid,"admin");
        if (rows==0)
            throw new ServiceException("此纪录可能已经不存在");
        return rows;
    }
    //分页查询
    @Transactional(readOnly = true)//只读
    @RequiredLog(operation = "分页查询")//拿到操作名称---要拿到这个注解---要先拿到这个方法
    //@Override
    public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
      //参数校验pageCurrent,总记录数.开始页面,页面大小
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("当前页码值无效");
        //2.查询总记录数并进行校验
        int rowCount=sysUserDao.getRowCount(username);
        if(rowCount==0)
            throw new ServiceException("没有找到对应记录");
        //3.查询当前页记录
        int pageSize=3;
        int startIndex=(pageCurrent-1)*pageSize;
        List<SysUserDeptVo> records=
                sysUserDao.findPageObjects(username,
                        startIndex, pageSize);
    //封装并返回结果
        return new PageObject<>(pageCurrent,pageSize,rowCount, records);
    }
}
