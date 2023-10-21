package com.fshuai.controller.teacher;

import com.fshuai.constant.JwtClaimsConstant;
import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherLoginDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
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
        // jwt令牌中存放老师的权限类型
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.TEA_ID, teacher.getId());
        claims.put(JwtClaimsConstant.TEA_ROLE, teacher.getRole());
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
    public Result save(@RequestBody TeacherDTO teacherDTO) {
        teacherService.save(teacherDTO);
        return Result.success();
    }

    @ApiOperation("修改教师")
    @PutMapping
    public Result update(@RequestBody TeacherDTO teacherDTO) {
        teacherService.update(teacherDTO);
        return Result.success();
    }

    @ApiOperation("教师分页查询")
    @GetMapping("/page")
    public Result<PageResult> getPage(TeacherPageQueryDTO teacherPageQueryDTO) {
        PageResult pageResult = teacherService.pageQuery(teacherPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("批量删除教师")
    @DeleteMapping()
    public Result<PageResult> deleteByIds(@RequestParam List<Long> ids) {
        teacherService.deleteBatch(ids);
        return Result.success();
    }

}
