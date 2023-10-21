package com.fshuai.service;

import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherLoginDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.Teacher;
import com.fshuai.result.PageResult;

import java.util.List;

public interface TeacherService {
    Teacher login(TeacherLoginDTO loginDTO);

    void save(TeacherDTO loginDTO);

    void update(TeacherDTO teacherDTO);

    PageResult pageQuery(TeacherPageQueryDTO teacherPageQueryDTO);

    void deleteBatch(List<Long> ids);
}
