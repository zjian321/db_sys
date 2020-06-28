package com.example.demo.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Order(2)
@Component
@Aspect
public class SysTimeAspect {

    @Pointcut("bean(sysUserServiceImpl)")
    public void doTime(){}

    /**
     * 目标方法执行 之前执行-------2
     * @param jp
     */
    @Before("doTime()")
    public void doBefore(JoinPoint jp){
        System.out.println("time doBefore()");
    }

    /**
     * 方法执行结束后执行-----4
     */
    @After("doTime()")
    public void doAfter(){
        System.out.println("time doAfter()");
    }

    /**
     * 执行正常 结束后执行的-------3
     */
    @AfterReturning("doTime()")
    public void doAfterReturning(){
        System.out.println("time doAfterReturning");
    }
    /**
     * 核心业务出现异常时执行说明：假如有after，先执行after,再执行Throwing
     */
    @AfterThrowing("doTime()")
    public void doAfterThrowing(){
        System.out.println("time doAfterThrowing");
    }

    /**
     * 切入点开始.进入切入方法------1
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("doTime()")
    public Object doAround(ProceedingJoinPoint jp)
            throws Throwable{
        System.out.println("doAround.before");
        try{
            Object obj=jp.proceed();
            //5---无论对错都会执行--------------5
            System.out.println("doAround.after");
            return obj;
        }catch(Throwable e){
            System.out.println(e.getMessage());
            throw e;
        }

    }

}







