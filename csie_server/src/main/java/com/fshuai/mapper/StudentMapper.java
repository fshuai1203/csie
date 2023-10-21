package com.fshuai.mapper;

import com.fshuai.entity.Student;
import com.fshuai.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student selectByIdNumber(String idNumber);

    void insert(Student student);

    Student selectById(Integer studentID);

    StudentVO selectStudentVoById(Integer studentID);

    void updateById(Student student);

    List<StudentVO> selectStudentVoByIds(List<Integer> memberIds);

    List<String> selectStudentNameByIds(List<Integer> memberIds);
}
