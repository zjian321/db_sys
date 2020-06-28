package com.example.demo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 匹配有此注解描述的方法
 */
@Retention(RetentionPolicy.RUNTIME)//定义何时有效
@Target(ElementType.METHOD)//定义可以描述的对象
public @interface RequiredCache {//RequiredCache.class
    String value() default "";
}
