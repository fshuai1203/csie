package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
import com.fshuai.constant.StatusConstant;
import com.fshuai.dto.StudentDTO;
import com.fshuai.dto.StudentLoginDTO;
import com.fshuai.dto.StudentRegisterDTO;
import com.fshuai.entity.Student;
import com.fshuai.exception.RegisterFailedException;
import com.fshuai.mapper.StudentMapper;
import com.fshuai.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
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
        return null;
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

    }
}
