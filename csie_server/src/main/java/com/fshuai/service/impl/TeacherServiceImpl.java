package com.fshuai.service.impl;

import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherLoginDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.Teacher;
import com.fshuai.result.PageResult;
import com.fshuai.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Override
    public Teacher login(TeacherLoginDTO loginDTO) {
        return null;
    }

    @Override
    public void save(TeacherDTO loginDTO) {

    }

    @Override
    public void update(TeacherDTO teacherDTO) {

    }

    @Override
    public PageResult pageQuery(TeacherPageQueryDTO teacherPageQueryDTO) {
        return null;
    }

    @Override
    public void deleteBatch(List<Long> ids) {

    }
}
