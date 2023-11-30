package com.fshuai.mapper;

import com.fshuai.entity.ProjectDeadline;
import com.fshuai.vo.DepartmentVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartMapper {


    @Select("select * from department")
    List<DepartmentVO> select();




}
