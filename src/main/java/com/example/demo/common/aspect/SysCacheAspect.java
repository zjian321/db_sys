package com.example.demo.common.aspect;

import com.example.demo.common.cache.DefaultMapCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class SysCacheAspect {
    @Autowired
    private DefaultMapCache mapCache;
    /**
     * 定义切入点:有RequiredCache注解描述的方法为切入点方法
     */
    @Pointcut("@annotation(com.example.demo.common.annotation.RequiredCache)")
    public void doCache(){}
    //
    @Pointcut("@annotation(com.example.demo.common.annotation.ClearCache)")
    public void doClear(){}

    @AfterReturning("doClear()")//方法正常执行结束后执行清楚缓存---环绕通知后面用这个
    public void doAfterReturning(){
        mapCache.clear();
    }

    @Around("doCache()")
    public Object doAround(ProceedingJoinPoint jp)throws Throwable{
        //System.out.println("Get data from cache");
        Object obj=mapCache.getObject("deptCache");
        if (obj!=null)return obj;
        Object result=jp.proceed();//目标方法的执行结果会赋予result
        //System.out.println("put data to cache");
        mapCache.putObject("deptCache",result);
        return result;
    }


}
