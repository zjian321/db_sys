package com.example.demo.common.vo;
/**
 * 修改页面的显示
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRoleMenuVo implements Serializable {
    private static final long serialVersionUID = -8010062366052178577L;
    private Integer id;
    private String name;
    private String note;
    private List<Integer> menuIds;
}
