package com.example.demo.common.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultMapCache {

    //使用map对象作为存储数据的容器---切面中拿到的缓存放在存储
    private Map<Object,Object> cache=new ConcurrentHashMap<>();//线程安装全的map
    public void putObject(Object key,Object value){
        cache.put(key,value);
    }
    public Object getObject(Object key){
        return cache.get(key);
    }
    public void clear(){
        cache.clear();
    }
}
