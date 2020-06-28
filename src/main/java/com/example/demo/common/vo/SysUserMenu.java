package com.example.demo.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserMenu implements Serializable {
    private static final long serialVersionUID = -5232844869060197126L;
    private Integer id;
    private String name;
    private String url;
    private List<SysUserMenu> childs;

}
