package com.fshuai.service;

import com.fshuai.dto.*;
import com.fshuai.entity.Student;
import com.fshuai.entity.Teacher;
import com.fshuai.result.PageResult;

import java.util.List;

public interface StudentService {
    Student login(StudentLoginDTO loginDTO);

    void register(StudentRegisterDTO studentRegisterDTO);

    void update(StudentDTO studentDTO);

    void updatePassword(UpdatePasswordDTO updatePasswordDTO);

    void logout();

}
