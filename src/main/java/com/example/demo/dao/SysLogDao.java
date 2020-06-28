package com.example.demo.dao;

import com.example.demo.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogDao {

    /**
     * 将日志信息写入到数据库
     * @param entity
     * @return
     */
    int insertObject(SysLog entity);

    /**
     * 查询总记录数
     * @return
     */
    int getRowCount(String username);

    /**
     * 分页查询
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SysLog> findPageObjects(String username, Integer startIndex, Integer pageSize);

    /**
     * 基于id删除日志
     * @param ids
     * @return
     */
    int deleteObjects(Integer...ids);
}
