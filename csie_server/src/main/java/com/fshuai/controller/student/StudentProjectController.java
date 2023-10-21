package com.fshuai.controller.student;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/project")
@Api(tags = "学生项目相关接口")
@Slf4j
public class StudentProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/page")
    @ApiOperation("查询自己参加的项目")
    public Result<PageResult> getMyProjects() {
        PageResult pageResult = projectService.getMyProject();
        return Result.success(pageResult);
    }

    @PostMapping("/apply")
    @ApiOperation("项目申报")
    public Result applyProject(@RequestBody ProjectDTO projectDTO) {
        projectService.addProject(projectDTO);
        return Result.success();
    }

    @PostMapping("/review")
    @ApiOperation("提交审核报告")
    public Result applyProject(@RequestBody ProjectReviewAttachmentsDTO attachmentsDTO) {
        projectService.reviewProject(attachmentsDTO);
        return Result.success();
    }

    @PostMapping("/members")
    @ApiOperation("人员变动")
    public Result updateProjectMembers(@RequestBody ProjectMemberDTO projectMemberDTO) {
        projectService.updateMember(projectMemberDTO);
        return Result.success();
    }

    @PostMapping("/finance")
    @ApiOperation("财务报销")
    public Result applyProjectFinance(@RequestBody ProjectFinanceDTO projectFinanceDTO) {
        projectService.addProjectFinance(projectFinanceDTO);
        return Result.success();
    }

}
