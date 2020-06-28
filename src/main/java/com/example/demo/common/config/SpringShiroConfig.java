package com.example.demo.common.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class SpringShiroConfig {

    //配置SessionManager会话对象
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        return sessionManager;
    }
    //实现记住我功能
    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager=new CookieRememberMeManager();
        SimpleCookie cookie=new SimpleCookie("rememberMe");
        cookie.setMaxAge(7*24*3600);
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    //CacheManager这个是管理cache的对象
    //配置授权cache
    @Bean
    public CacheManager shiroCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 配置securtyManger(注意报名)此对象用户实现用户身份认证和授权等功能
     * @Bean注解一般要结合@Configuration注解使用,用于描述方法,表示这个方法
     * 的返回值交给spring管理.key默认方法名或者直接由@Bean直接指定
     * @return
     */
    @Bean
    //@Scope("singleton")默认为单例
    public SecurityManager securityManager(Realm realm,
                                           CacheManager cacheManager,
                                           RememberMeManager rememberMeManager,
                                           SessionManager sessionManager){
        DefaultWebSecurityManager securityManager=
                new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(rememberMeManager);//设置cookie
        securityManager.setSessionManager(sessionManager);//设置会话储存时长
        return securityManager;
    }
    //认证拦截的实现

    /**
     * subject(提交)-->SecurityManager拿到数据和数据库数据(Realm)做对比(认证)
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory (SecurityManager securityManager){
        //1.创建ShiroFilterFactoryBean对象
        //1.1构建对象----工厂对象
        ShiroFilterFactoryBean sfBean=new ShiroFilterFactoryBean();
        //1.2设置安全认证授权对象
        sfBean.setSecurityManager(securityManager);
        //1.3设置登录页面
        sfBean.setLoginUrl("/doLogin");
        //2.设置锅炉规则
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"()例如项目static目录下的资源
        map.put("/bower_components/**","anon");
        map.put("/modules/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        map.put("/user/doLogin","anon");
        map.put("/doLogout","logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        //map.put("/**","authc");//
        map.put("/**","user");//当选择了记住我功能,认证方式进行修改
        sfBean.setFilterChainDefinitionMap(map);
        return sfBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor (
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
