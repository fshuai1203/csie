package com.fshuai.controller.teacher;

import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.dto.ProjectDeadlineUpdateDTO;
import com.fshuai.result.Result;
import com.fshuai.service.DeadlineService;
import com.fshuai.vo.ProjectDeadlineVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result updateDeadLine(@RequestBody ProjectDeadlineUpdateDTO projectDeadlineUpdateDTO) {
        deadlineService.updateDeadLine(projectDeadlineUpdateDTO);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("添加大创时间")
    public Result addDeadLine(@RequestBody ProjectDeadlineDTO projectDeadlineDTO) {
        deadlineService.addDeadLine(projectDeadlineDTO);
        return Result.success();
    }

}
