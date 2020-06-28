package com.example.demo.Service.impl;

import com.example.demo.Service.SysLogService;
import com.example.demo.common.bo.PageObject;
import com.example.demo.common.exception.ServiceException;
import com.example.demo.dao.SysLogDao;
import com.example.demo.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {


    @Autowired
    private SysLogDao sysLogDao;

    //写入日志
    //异步写日志操作,同样使用的时AOP
    //@Async描述的方法为切入
    //这个切入点上执行的异步操作为通知(Advice
    // )
    @Async//加入异步@Async,告诉spring这个方法运行在异步线程上(此线程有spring线程池提供)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveObject(SysLog entity) {
        sysLogDao.insertObject(entity);
    }

    @Override
    public int deleteObjects(Integer... ids) {
        //1.参数校验
        if (ids==null||ids.length==0)
            throw new IllegalArgumentException("请先选择id");
        int rows;
        try {
            rows = sysLogDao.deleteObjects(ids);
        }catch (Throwable e){
            e.printStackTrace();
            throw new ServiceException("系统维护中...");
        }
        if (rows==0)
            throw new ServiceException("记录可能已经不存在");
        return rows;
    }

    @Override
    public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
       //1.验证参数的合法性
        //1.1验证pagecCurrent,
        if (pageCurrent==null || pageCurrent<1)
            throw new IllegalArgumentException("当前页码值不合法");
        //2.查询总记录数 验证
        int rowCount = sysLogDao.getRowCount(username);
        if (rowCount==0)
            throw new ServiceException("系统没有对应的记录");
        //3.设定页面大小pageSize
        Integer pageSize=3;
        //4.计算开始位置
        Integer startIndex=(pageCurrent-1)*pageSize;
        //5.查询当前页记录
        List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
        PageObject<SysLog> pageObjece=new  PageObject<>();
        pageObjece.setPageCurrent(pageCurrent);
        pageObjece.setPageSize(pageSize);
        pageObjece.setRowCount(rowCount);
        pageObjece.setRecords(records);
        pageObjece.setPageCount((rowCount-1)/pageSize+1);
        return pageObjece;
    }
}
