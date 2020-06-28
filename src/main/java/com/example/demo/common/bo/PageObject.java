package com.example.demo.common.bo;
/**
 * 用来封装查从service查询寻的数据
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageObject<T> implements Serializable {
/**1.当前页码值*/
private Integer pageCurrent;
/**2.页面大小*/
private Integer pageSize;
/**3.总行数*/
private Integer rowCount;
/**4.总页数*/
private Integer pageCount;
/**5.当前页记录*/
private List<T> records;

    public PageObject() {}
    public PageObject(Integer pageCurrent, Integer pageSize, Integer rowCount, List<T> records) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.records = records;
        this.pageCount =(rowCount-1)/pageSize+1;
    }
}
