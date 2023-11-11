package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
import com.fshuai.constant.PasswordConstant;
import com.fshuai.constant.RoleConstant;
import com.fshuai.constant.StatusConstant;
import com.fshuai.context.TeacherBaseContext;
import com.fshuai.dto.*;
import com.fshuai.entity.Teacher;
import com.fshuai.exception.*;
import com.fshuai.mapper.TeacherMapper;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.service.TeacherService;
import com.fshuai.vo.TeacherVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 教师登陆
     *
     * @param loginDTO
     * @return
     */
    @Override
    public Teacher login(TeacherLoginDTO loginDTO) {
        // 检查字段
        if (loginDTO.getIdNumber() == null || loginDTO.getIdNumber().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FIELD_EMPTY);
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FIELD_EMPTY);
        }
        Teacher teacherFromDB = teacherMapper.selectByIdNumber(loginDTO.getIdNumber());
        // 检查学号
        if (teacherFromDB == null || teacherFromDB.getIdNumber().isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 检查用户状态
        if (teacherFromDB.getState() == StatusConstant.DISABLE) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 检查密码是否正确
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), teacherFromDB.getPassword())) {
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }
        return teacherFromDB;
    }

    /**
     * 添加教师
     *
     * @param teacherRegisterDTO
     */
    @Override
    public void save(TeacherRegisterDTO teacherRegisterDTO) {
        // 检查各个字段是否为空
        if (teacherRegisterDTO.getIdNumber() == null || teacherRegisterDTO.getIdNumber().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getName() == null || teacherRegisterDTO.getName().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getSex() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getDeptId() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getMajorName() == null || teacherRegisterDTO.getMajorName().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getPhone() == null || teacherRegisterDTO.getPhone().isEmpty()) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherRegisterDTO.getRole() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有负责人才能添加
        if (teacherRole == RoleConstant.GUIDE_TEACHER || teacherRole == RoleConstant.CHECK_TEACHER) {
            throw new RegisterFailedException(MessageConstant.ROLE_FAILED);
        }
        // 检查老师的职工号是否注册
        // 根据idNumber查询数据库，检查学生是否存在
        Teacher teacherByDB = teacherMapper.selectByIdNumber(teacherRegisterDTO.getIdNumber());
        if (teacherByDB != null) {
            throw new RegisterFailedException(MessageConstant.ID_NUMBER_HAS_EXISTS);
        }
        // 添加校级负责人(dept=0)
        Teacher teacher = Teacher.builder().build();
        BeanUtils.copyProperties(teacherRegisterDTO, teacher);
        // 查看用户身份
        // 对于系级负责人来说，如果添加外系的人则无法添加
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD && teacherDept != teacherRegisterDTO.getDeptId()) {
            throw new RegisterFailedException(MessageConstant.ROLE_FAILED);
        }
        // 添加校级负责人
        if (teacherRegisterDTO.getRole() == RoleConstant.SCHOOL_HEAD) {
            if (teacherRole == RoleConstant.DEPARTMENT_HEAD) {
                throw new RegisterFailedException(MessageConstant.ROLE_FAILED);
            }
            // 不满足规定，校级负责人role=1，dept=0
            if (teacherRegisterDTO.getDeptId() != 0) {
                throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_ILLEGAL);
            }
            teacher.setPassword(bCryptPasswordEncoder.encode(PasswordConstant.SCHOOL_HEAD_DEFAULT_PASSWORD));
        }
        // 添加系级负责人
        if (teacherRegisterDTO.getRole() == RoleConstant.DEPARTMENT_HEAD) {
            teacher.setPassword(bCryptPasswordEncoder.encode(PasswordConstant.DEPARTMENT_HEAD_DEFAULT_PASSWORD));
        }
        // 添加系级指导教师
        if (teacherRegisterDTO.getRole() == RoleConstant.GUIDE_TEACHER) {
            teacher.setPassword(bCryptPasswordEncoder.encode(PasswordConstant.GUIDE_TEACHER_DEFAULT_PASSWORD));
        }
        // 添加系级审核教师
        if (teacherRegisterDTO.getRole() == RoleConstant.DEPARTMENT_HEAD) {
            teacher.setPassword(bCryptPasswordEncoder.encode(PasswordConstant.DEPARTMENT_HEAD_DEFAULT_PASSWORD));
        }
        teacher.setState(StatusConstant.ENABLE);
        teacherMapper.insert(teacher);
    }

    /**
     * 初始化系统管理员
     */
    @Override
    public void initAdmin() {
        Integer count = teacherMapper.selectCount();
        // 如果当前系统有老师则不能进行初始化
        if (count != 0) {
            throw new TeacherProjectException(MessageConstant.SYSTEM_INIT_FAILED);
        }
        Teacher teacher = Teacher
                .builder()
                .name("管理员")
                .idNumber("admin")
                .sex(1)
                .deptId(0)
                .majorName("系统管理员")
                .password(bCryptPasswordEncoder.encode(PasswordConstant.ADMIN_DEFAULT_PASSWORD))
                .phone("13315927816")
                .role(RoleConstant.SCHOOL_HEAD)
                .state(StatusConstant.ENABLE)
                .build();
        teacherMapper.insert(teacher);
    }

    @Override
    public TeacherVO getByIdNumber(String idNumber) {
        TeacherVO teacherVO = teacherMapper.selectTeacherVOByIdNumber(idNumber);
        if (teacherVO == null) {
            throw new TeacherProjectException(MessageConstant.TEACHER_ID_NUMBER_ERROR);
        }
        return teacherVO;
    }

    /**
     * 更改权限
     * 校级改全部
     * 系级只能改自己系
     * 还要防止系级把校级的权限进行降级
     *
     * @param teacherUpdateDTO
     */
    @Override
    public void updateRole(TeacherUpdateDTO teacherUpdateDTO) {
        // 检查修改字段
        if (teacherUpdateDTO.getIdNumber() == null || teacherUpdateDTO.getIdNumber().isEmpty()) {
            throw new TeacherUpdateFailedException(MessageConstant.EDIT_FAILED_EMPTY);
        }
        Teacher teacherByDB = teacherMapper.selectByIdNumber(teacherUpdateDTO.getIdNumber());
        // 如果数据库中不存在当前用户，则不能修改
        if (teacherByDB == null) {
            throw new TeacherUpdateFailedException(MessageConstant.ID_NUMBER_HAS_EXISTS);
        }
        // 如果修改字段为空，则不能修改
        if (teacherUpdateDTO.getRole() == null || teacherUpdateDTO.getDeptId() == null) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_EMPTY);
        }
        if (teacherUpdateDTO.getRole() == RoleConstant.SCHOOL_HEAD && teacherUpdateDTO.getDeptId() != 0) {
            throw new RegisterFailedException(MessageConstant.REGISTER_FIELD_ILLEGAL);
        }
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 校级负责人可以修改全部
        if (teacherRole == RoleConstant.SCHOOL_HEAD) {
            update(teacherUpdateDTO);
            return;
        }
        // 系级负责人只能修改本系，不能把校级负责人的权限降低
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD && teacherUpdateDTO.getDeptId() == teacherDept) {
            // 如果要把校级负责人降级则不允许修改
            if (teacherByDB.getRole() == RoleConstant.SCHOOL_HEAD) {
                throw new TeacherUpdateFailedException(MessageConstant.ROLE_FAILED);
            }
            // 如果要修改成校级负责人则不允许系级负责人修改
            if (teacherUpdateDTO.getRole() == RoleConstant.SCHOOL_HEAD) {
                throw new TeacherUpdateFailedException(MessageConstant.ROLE_FAILED);
            }
            update(teacherUpdateDTO);
            return;
        }
        throw new TeacherUpdateFailedException(MessageConstant.ROLE_FAILED);
    }


    /**
     * 修改教师
     *
     * @param updateDTO
     */
    public void update(TeacherUpdateDTO updateDTO) {
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有负责人才能添加
        if (teacherRole == RoleConstant.GUIDE_TEACHER || teacherRole == RoleConstant.CHECK_TEACHER) {
            throw new RegisterFailedException(MessageConstant.ROLE_FAILED);
        }
        Teacher teacher = Teacher.builder().build();
        BeanUtils.copyProperties(updateDTO, teacher);
        teacherMapper.updateByIdNumber(teacher);
    }

    /**
     * 查询老师
     *
     * @param teacherPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(TeacherPageQueryDTO teacherPageQueryDTO) {
        PageHelper.startPage(teacherPageQueryDTO.getPage(), teacherPageQueryDTO.getPageSize());
        Page<TeacherVO> page = teacherMapper.pageQuery(teacherPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除用户
     * 系级教师只能删除系级的老师
     * 校级老师可以删除所有老师
     *
     * @param ids
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 校级负责人可以删除所有人
        if (teacherRole == RoleConstant.SCHOOL_HEAD) {
            teacherMapper.deleteBatchByIds(ids);
            return;
        }
        // 系级负责人只能删除本系的人,且权限低的老师
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD) {
            // 检查ids里面有没有校级负责人和其他系的老师
            // 如果为空，则说明ids里面的老师都能够删除
            if (teacherMapper.selectByIdsCheckRoleOrNotDept(ids, RoleConstant.SCHOOL_HEAD, teacherDept).isEmpty()) {
                teacherMapper.deleteBatchByIds(ids);
                return;
            }
        }
        throw new DeletionNotAllowedException(MessageConstant.ROLE_FAILED);
    }

}
