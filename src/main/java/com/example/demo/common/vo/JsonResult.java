package com.example.demo.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 2734115307169476835L;
    private int state=1;//1表示SUCCESS,0表示ERROR
    /**状态信息*/
    private String message="ok";
    /**正确数据*/
    private Object data;
    public JsonResult() {}
    /**出现异常调用*/
    public JsonResult(Throwable t) {
        this.state=0;
        this.message=t.getMessage();
    }
    public JsonResult(String message) {
        this.message = message;
    }
    /**封装正常查询的结果*/
    public JsonResult(Object data) {
        this.data = data;
    }
}
