package com.example.demo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 通过该类,记录异常日志的记录
 */
@Slf4j
@Aspect
@Component
public class SysExceptionLogAspect {

    //@Pointcut("bean(sysUserServiceImpl)")
    //public void doExceptionPointCut(){}

    //异常还是会抛出,
    @AfterThrowing(value = "bean(sysUserServiceImpl)", throwing = "e")
    public void doHandleException(JoinPoint jp, Throwable e) {
        MethodSignature ms = (MethodSignature) jp.getSignature();
        log.error("{} exception msg is  {}", ms.getName(), e.getMessage());

    }
}