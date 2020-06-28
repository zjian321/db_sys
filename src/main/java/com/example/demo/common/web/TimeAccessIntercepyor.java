package com.example.demo.common.web;

import com.example.demo.common.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class TimeAccessIntercepyor implements HandlerInterceptor {

    //此方法会在@Controller对象方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("控制层访问拦截");
        //1.获取当前日历对象
        Calendar c = Calendar.getInstance();
        //2.设置访问时间
        c.set(Calendar.HOUR_OF_DAY,0);//上午8点开始可登录
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        long start=c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY,12);//下午16点开始不可登录
        long end=c.getTimeInMillis();
        long currentTime = System.currentTimeMillis();
        if (currentTime<start||currentTime>end)
            throw new ServiceException("请在范围之间内访问");
        return true;//fslse标识拒绝执行
    }
}
