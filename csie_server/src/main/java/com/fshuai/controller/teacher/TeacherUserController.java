package com.fshuai.controller.teacher;

import com.fshuai.constant.JwtClaimsConstant;
import com.fshuai.dto.*;
import com.fshuai.entity.Teacher;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.TeacherService;
import com.fshuai.utils.JwtUtil;
import com.fshuai.vo.TeacherLoginVO;
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
@RequestMapping("/teacher/user")
@Api(tags = "教师端用户相关接口")
@Slf4j
public class TeacherUserController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtProperties jwtProperties;


    @PostMapping("/login")
    @ApiOperation("教师登陆系统")
    public Result<TeacherLoginVO> login(@RequestBody TeacherLoginDTO loginDTO) {
        Teacher teacher = teacherService.login(loginDTO);

        // 登陆失败抛出异常被捕获
        // 登录成功后，生成jwt令牌
        // jwt令牌中存放老师的id和权限类型
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.TEA_ID, teacher.getId());
        claims.put(JwtClaimsConstant.TEA_ROLE, teacher.getRole());
        claims.put(JwtClaimsConstant.TEA_DEPT, teacher.getDeptId());
        log.info(claims.toString());
        String token = JwtUtil.createJWT(
                jwtProperties.getTeacherSecretKey(),
                jwtProperties.getTeacherTtl(),
                claims);

        // 构建教师数据并返回
        TeacherLoginVO teacherLoginVO = TeacherLoginVO.builder()
                .token(token)
                .build();
        BeanUtils.copyProperties(teacher, teacherLoginVO);

        return Result.success(teacherLoginVO);
    }

    @ApiOperation("添加教师")
    @PostMapping("")
    public Result save(@RequestBody TeacherRegisterDTO teacherRegisterDTO) {
        teacherService.save(teacherRegisterDTO);
        return Result.success();
    }

    @ApiOperation("修改教师权限")
    @PutMapping("/role")
    public Result update(@RequestBody TeacherUpdateDTO teacherUpdateDTO) {
        teacherService.updateRole(teacherUpdateDTO);
        return Result.success();
    }

    @ApiOperation("教师分页查询")
    @GetMapping("/page")
    public Result<PageResult> getPage(TeacherPageQueryDTO teacherPageQueryDTO) {
        log.info("请求分页查询老师数据{}",teacherPageQueryDTO);
        PageResult pageResult = teacherService.pageQuery(teacherPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 传入教师信息用于检查权限
     * @param teachers
     * @return
     */
    @ApiOperation("批量删除教师")
    @DeleteMapping()
    public Result<PageResult> deleteByIds(@RequestBody List<TeacherDTO> teachers) {
        teacherService.deleteBatch(teachers);
        return Result.success();
    }

}
