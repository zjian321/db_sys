package com.example.demo.Service;

import com.example.demo.common.bo.PageObject;
import com.example.demo.entity.SysLog;

public interface SysLogService {

    /**
     * 保存日志信息
     * @param entity
     */
    void saveObject(SysLog entity);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteObjects(Integer...ids);

    /**
     * 查询
     * @param username
     * @param pageCurrent
     * @return
     */
    PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);

}
