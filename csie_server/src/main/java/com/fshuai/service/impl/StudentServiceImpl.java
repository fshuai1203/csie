package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
import com.fshuai.constant.StatusConstant;
import com.fshuai.context.BaseContext;
import com.fshuai.dto.StudentDTO;
import com.fshuai.dto.StudentLoginDTO;
import com.fshuai.dto.StudentRegisterDTO;
import com.fshuai.dto.UpdatePasswordDTO;
import com.fshuai.entity.Student;
import com.fshuai.exception.LoginFailedException;
import com.fshuai.exception.PasswordEditFailedException;
import com.fshuai.exception.PasswordErrorException;
import com.fshuai.exception.RegisterFailedException;
import com.fshuai.mapper.StudentMapper;
import com.fshuai.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 学生登陆
     *
     * @param loginDTO
     * @return
     */
    @Override
    public Student login(StudentLoginDTO loginDTO) {
        // 检查字段
        if (loginDTO.getIdNumber() == null || loginDTO.getIdNumber().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FIELD_EMPTY);
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FIELD_EMPTY);
        }
        Student studentFromDB = studentMapper.selectByIdNumber(loginDTO.getIdNumber());
        // 检查学号
        if (studentFromDB == null || studentFromDB.getIdNumber().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 检查密码是否正确
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), studentFromDB.getPassword())) {
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }
        return studentFromDB;
    }

    /**
     * 学生注册
     *
     * @param studentRegisterDTO
     */
    @Override
    public void register(StudentRegisterDTO studentRegisterDTO) {
        // 检查各个字段是否为空
        if (studentRegisterDTO.getIdNumber() == null || studentRegisterDTO.getIdNumber().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getName() == null || studentRegisterDTO.getName().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getSex() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getDeptId() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getMajorId() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getPassword() == null || studentRegisterDTO.getPassword().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (studentRegisterDTO.getPhone() == null || studentRegisterDTO.getPhone().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }


        // 根据idNumber查询数据库，检查学生是否存在
        Student studentByDB = studentMapper.selectByIdNumber(studentRegisterDTO.getIdNumber());
        if (studentByDB != null) {
            throw new RegisterFailedException(MessageConstant.ID_NUMBER_HAS_EXISTS);
        }
        // 密码加密
        studentRegisterDTO.setPassword(bCryptPasswordEncoder.encode(studentRegisterDTO.getPassword()));
        Student student = Student.builder().build();
        BeanUtils.copyProperties(studentRegisterDTO, student);
        student.setState(StatusConstant.ENABLE);
        studentMapper.insert(student);
    }

    /**
     * 学生信息更新
     *
     * @param studentDTO
     */
    @Override
    public void update(StudentDTO studentDTO) {
        // 获取学生ID
        Integer studentID = BaseContext.getCurrentId();
        Student student = Student.builder()
                .id(studentID)
                .build();
        BeanUtils.copyProperties(studentDTO, student);
        studentMapper.updateById(student);
    }

    /**
     * 更新密码
     * 从token中获取当前用户信息
     *
     * @param updatePasswordDTO
     */
    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        // 获取学生ID
        Integer studentID = BaseContext.getCurrentId();
        // 根据ID查询学生信息
        Student studentByDB = studentMapper.selectById(studentID);
        // 检查旧密码是否匹配,并捕获异常进行处理
        if (!bCryptPasswordEncoder.matches(updatePasswordDTO.getOldPassword(), studentByDB.getPassword())) {
            throw new PasswordEditFailedException(MessageConstant.OLD_PASSWORD_ERROR);
        }

        // 此时旧密码匹配成功，可以修改密码
        StudentDTO studentDTO = StudentDTO.builder()
                .password(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()))
                .build();

        update(studentDTO);
    }

    @Override
    public void logout() {
        //：todo 使jwt失效
    }
}
