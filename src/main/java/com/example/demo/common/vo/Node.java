package com.example.demo.common.vo;
/**
 * 菜单添加父类信息
 */

import lombok.Data;
import java.io.Serializable;

@Data
public class Node implements Serializable {
    private static final long serialVersionUID = 6109708711392985651L;
    private Integer id;
    private String name;
    private Integer parentId;
}
