package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = -8860634250367611018L;
    private Integer id;
    private String name;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}
