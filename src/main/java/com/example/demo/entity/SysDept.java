package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysDept implements Serializable {
        private static final long serialVersionUID = 8876920804134951849L;
        private Integer id;
        private String name;
        private Integer parentId;
        private Integer sort;
        private String note;
        private Date createdTime;
        private Date modifiedTime;
        private String createdUser;
        private String modifiedUser;
    }

