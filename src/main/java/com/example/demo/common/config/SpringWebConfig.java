package com.example.demo.common.config;

import com.example.demo.common.web.TimeAccessIntercepyor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeAccessIntercepyor())//添加拦截对象
                .addPathPatterns("/user/doLogin");//设置对登录操作进行拦截

    }
}
