package com.example.demo.Service;

import com.example.demo.dao.SysMenuDao;
import com.example.demo.dao.SysRoleMenuDao;
import com.example.demo.dao.SysUserDao;
import com.example.demo.dao.SysUserRoleDao;
import com.example.demo.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基于此对象获取用户认证和授权信息并进行封装
 */
@Component
public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    /**负责授权信息的收取和封装:
     * 为了提高授权心梗,还可以将用户权限信息进行缓存,具体缓存对象,底层使用的时SoftHashMao,
     * 这个容易的key为当前用户身份,doGetAuthorizationInfo的返回值作为value储存到SoftHashMap
     * 有缓存就可以不用在查数据库了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限授权信息");
        //1.获取登录用户信息
        SysUser user=(SysUser)principals.getPrimaryPrincipal();
        //2.基于登录用户id获取用户角色id并进行判定
        List<Integer> roleIds=sysUserRoleDao.findRoleIdByUserId(user.getId());
        if (roleIds==null || roleIds.size()==0)
            throw new AuthorizationException();
        //3.基于角色id获取角色对应的菜单id并进行校验
        List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
        if (menuIds==null || menuIds.size()==0)
            throw new AuthorizationException();
        //4.基于菜单id获取菜单收钱标识(oerisssion)
        Integer[] array={};
        List<String> permission=sysMenuDao.findPermissions(menuIds.toArray(array));
        if (permission==null || permission.size()==0)
            throw new AuthorizationException();
        //5.对权限标识信息进行封装
        Set<String> stringPermissions=new HashSet<>();
        for (String per:permission) {
             if (!StringUtils.isEmpty(per)){
                 stringPermissions.add(per);
             }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(stringPermissions);
        return info;
    }

    /**负责认证信息的获取和封装*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户输入的用户名份
        UsernamePasswordToken upToken=(UsernamePasswordToken)token;
        String username=upToken.getUsername();
        //2.基于用户名获取用户对象
        SysUser user=sysUserDao.findUserByUserName(username);
        //3.判定用户对象是否存在
        if (user==null)throw new UnknownAccountException();
        //4.判定用户是否有效(是否被锁定)
        if (user.getValid()==0)throw new LockedAccountException();
        //5.封装用户认证信息
        ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(
                                user,//用户身份
                                user.getPassword(),//已经加密的密码
                                credentialsSalt,//credentialsSalt凭证盐
                                getName());//real名称
        return info;
    }

    /**负责获取加密凭证匹配器对象*/
    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher hMatcher=new HashedCredentialsMatcher();
        hMatcher.setHashAlgorithmName("MD5");
        hMatcher.setHashIterations(1);
        return hMatcher;
    }



}
