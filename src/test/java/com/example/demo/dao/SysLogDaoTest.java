package com.example.demo.dao;

import com.example.demo.entity.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysLogDaoTest {

    /**
     *
     */
    @Autowired
    private SysLogDao sysLogDao;

    @Test
    public void testgetRowCount(){
        int rows = sysLogDao.getRowCount("admin");
        System.out.println(rows);
    }
    @Test
    public void testFindPageObjects(){
        List<SysLog> list=sysLogDao.findPageObjects("admin",0,10);
        for (SysLog log: list
             ) {
            System.out.println(log);
        }

    }
}
