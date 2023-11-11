package com.fshuai.service;

import com.fshuai.dto.*;
import com.fshuai.entity.Teacher;
import com.fshuai.result.PageResult;
import com.fshuai.vo.TeacherVO;

import java.util.List;

public interface TeacherService {
    Teacher login(TeacherLoginDTO loginDTO);

    void save(TeacherRegisterDTO teacherPageDTO);

    void updateRole(TeacherUpdateDTO teacherUpdateDTO);

    PageResult pageQuery(TeacherPageQueryDTO teacherPageQueryDTO);

    void deleteBatch(List<Integer> ids);

    void initAdmin();

    TeacherVO getByIdNumber(String idNumber);
}
