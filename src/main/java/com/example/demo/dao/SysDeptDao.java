package com.example.demo.dao;

import com.example.demo.common.vo.Node;
import com.example.demo.entity.SysDept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDeptDao {

    @Select("select c.*,p.name parentName from sys_depts c left join sys_depts p on c.parentId=p.id")
    List<Map<String,Object>> findObjects();

    @Select("select id,name,parentId from sys_depts")
    List<Node> findZTreeNodes();

    int updateObject(SysDept entity);
    int insertObject(SysDept entity);

    @Select("select count(*) from sys_depts where parentId=#{id}")
    int getChildCount(Integer id);

    @Delete("delete from sys_depts where id=#{id}")
    int deleteObject(Integer id);
}
