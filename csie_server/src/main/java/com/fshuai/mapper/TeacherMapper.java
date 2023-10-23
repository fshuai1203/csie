package com.fshuai.mapper;

import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.Teacher;
import com.fshuai.vo.TeacherVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    Teacher selectByIdNumber(String idNumber);

    void insert(Teacher teacher);

    Teacher selectById(Integer teacherID);

    void updateByIdNumber(Teacher teacher);

    Page<TeacherVO> pageQuery(TeacherPageQueryDTO teacherPageQueryDTO);

    void deleteBatch(List<TeacherDTO> teachers);
}
