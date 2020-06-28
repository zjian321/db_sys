package com.example.demo.common.aspect;

import com.example.demo.Service.SysLogService;
import com.example.demo.common.annotation.RequiredLog;
import com.example.demo.common.utils.IPUtils;
import com.example.demo.common.utils.ShiroUtils;
import com.example.demo.entity.SysLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * AOP切面入门
 */
//@Order(1)
@Aspect
@Slf4j
@Component
public class SysLogAspect {

    //定义切面切入点,和方法
   // @Pointcut("bean(sysUserServiceImpl)")
    @Pointcut("@annotation(com.example.demo.common.annotation.RequiredLog)")
    public void doLogPointcut(){}

    //切面方法,环绕通知
    @Around("doLogPointcut()")
    public Object around(ProceedingJoinPoint jp)throws Throwable{
        System.out.println(" class SysLogAspect  切面入门");
        long start=System.currentTimeMillis();
        log.info("start {}",start);
        try {
        Object result=jp.proceed();
            long end=System.currentTimeMillis();
        log.info("end {}",end);
        //记录用户的正常行为信息
            saveLog(jp,(end-start));//基于此方法将用户行为信息写入到数据库中
        return result;
        }catch (Throwable e){
        log.error(e.getMessage());
        throw e;
        }
    }
    @Autowired
    private SysLogService sysLogService;
    //获取用户行为信息(谁在什么时间,执行了什么操作,什么方法,传递了什么参数)并记录
    private void saveLog(ProceedingJoinPoint jp,long time)throws Exception{
        //1.获取用户行为信息
        //1.获取IP地址
        String ip= IPUtils.getIpAddr();
        //1.2获取登录用户名
        //`SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
        String username= ShiroUtils.getUsername();
        //1.3获取目标方法上requiredLog注解指定的操作名
        //1.3.1获取目标方法对象
        //1.3.1.获取目标对象类型
        Class<?> tarGetClass=jp.getTarget().getClass();
        //1.3.1.2获取目标中目标方法
        MethodSignature ms=(MethodSignature)jp.getSignature();
        Method targetMethod=tarGetClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
        //1.3.2获取目标方法对象上的RequiredLog注解
        RequiredLog requiredLog=targetMethod.getAnnotation(RequiredLog.class);
        //1.3.3获取注解中指定的操作名
        String operation =null;
        if (requiredLog!=null) {//当切入点为注解表达式时,此句可以不要,因为是注解描述就不会为空
           operation = requiredLog.operation();
        }
        //1.4获取目标方法的类名以及方法名
        String method=tarGetClass.getName()+"."+targetMethod.getName();
        //1.5获取执行方法是传入的实际参数
        String params=new ObjectMapper().writeValueAsString(jp.getArgs());
        //2.对用户行为信息进行封装
        SysLog userLog=new SysLog();
        userLog.setIp(ip);
        userLog.setUsername(username);
        userLog.setOperation(operation);
        userLog.setMethod(method);
        userLog.setParams(params);
        userLog.setTime(time);
        userLog.setCreatedTime(new Date());
        //3.将用户信息写入到数据库
        sysLogService.saveObject(userLog);
    }
}
