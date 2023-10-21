package com.fshuai.controller.teacher;

import com.fshuai.constant.JwtClaimsConstant;
import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherLoginDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.Teacher;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.DeadlineService;
import com.fshuai.service.TeacherService;
import com.fshuai.utils.JwtUtil;
import com.fshuai.vo.ProjectDeadlineVO;
import com.fshuai.vo.TeacherLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teacher/deadline")
@Api(tags = "时间发布相关接口")
@Slf4j
public class DeadlineController {

    @Autowired
    DeadlineService deadlineService;

    @GetMapping
    @ApiOperation("获取大创时间")
    public Result<ProjectDeadlineVO> getDeadLine() {
        ProjectDeadlineVO projectDeadlineVO = deadlineService.getDeadline();
        return Result.success(projectDeadlineVO);
    }

    @PutMapping
    @ApiOperation("修改大创时间")
    public Result updateDeadLine(@RequestBody ProjectDeadlineDTO projectDeadlineDTO) {
        deadlineService.updateDeadLine(projectDeadlineDTO);
        return Result.success();
    }

}
