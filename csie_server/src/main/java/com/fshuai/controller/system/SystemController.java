package com.fshuai.controller.system;

import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.dto.ProjectDeadlineUpdateDTO;
import com.fshuai.result.Result;
import com.fshuai.service.DeadlineService;
import com.fshuai.service.TeacherService;
import com.fshuai.vo.ProjectDeadlineVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
@Api(tags = "系统相关接口")
@Slf4j
public class SystemController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/init")
    @ApiOperation("系统初始化,初始化管理员用户")
    public Result systemInit() {
        teacherService.initAdmin();
        return Result.success();
    }

}
