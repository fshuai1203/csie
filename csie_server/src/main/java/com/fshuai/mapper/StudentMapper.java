package com.fshuai.mapper;

import com.fshuai.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    Student selectByIdNumber(String idNumber);

    void insert(Student student);

    Student selectById(Integer studentID);

    void updateById(Student student);
}
