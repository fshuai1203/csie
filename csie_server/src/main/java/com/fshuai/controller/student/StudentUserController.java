package com.fshuai.controller.student;

import com.fshuai.constant.JwtClaimsConstant;
import com.fshuai.dto.*;
import com.fshuai.entity.Student;
import com.fshuai.entity.Teacher;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.StudentService;
import com.fshuai.service.TeacherService;
import com.fshuai.utils.JwtUtil;
import com.fshuai.vo.StudentLoginVO;
import com.fshuai.vo.StudentVO;
import com.fshuai.vo.TeacherLoginVO;
import com.fshuai.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/user")
@Api(tags = "学生端用户相关接口")
@Slf4j
public class StudentUserController {

    @Autowired
    StudentService studentService;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    TeacherService teacherService;


    @PostMapping("/login")
    @ApiOperation("学生登陆系统")
    public Result<StudentLoginVO> login(@RequestBody StudentLoginDTO loginDTO) {
        Student student = studentService.login(loginDTO);

        // 登陆失败抛出异常被捕获
        // 登录成功后，生成jwt令牌
        // jwt令牌中存放老师的权限类型
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.STU_ID, student.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getStudentSecretKey(),
                jwtProperties.getStudentTtl(),
                claims);

        // 构建教师数据并返回
        StudentLoginVO studentLoginVO = StudentLoginVO.builder()
                .token(token)
                .build();
        BeanUtils.copyProperties(student, studentLoginVO);

        return Result.success("登陆成功", studentLoginVO);
    }

    @ApiOperation("学生注册")
    @PostMapping("/register")
    public Result register(@RequestBody StudentRegisterDTO studentRegisterDTO) {
        studentService.register(studentRegisterDTO);
        return Result.success("学生注册成功");
    }

    @ApiOperation("学生信息修改")
    @PutMapping
    public Result update(@RequestBody StudentDTO studentDTO) {
        studentService.update(studentDTO);
        return Result.success();
    }

    @ApiOperation("修改密码")
    @PutMapping("/password")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        studentService.updatePassword(updatePasswordDTO);
        return Result.success("修改密码成功");
    }

    @ApiOperation("学生退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        studentService.logout();
        return Result.success();
    }

    @ApiOperation("根据学号查询学生")
    @GetMapping("/search/student")
    public Result<StudentVO> searchStudentByIdNumber(String idNumber) {
        StudentVO studentVO = studentService.getByIdNumber(idNumber);
        return Result.success(studentVO);
    }

    @ApiOperation("根据教职工号查询教师")
    @GetMapping("/search/teacher")
    public Result<TeacherVO> searchTeacherByIdNumber(String idNumber) {
        TeacherVO teacherVO = teacherService.getByIdNumber(idNumber);
        return Result.success(teacherVO);
    }


}
