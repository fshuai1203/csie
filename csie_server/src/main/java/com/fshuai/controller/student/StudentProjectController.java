package com.fshuai.controller.student;

import com.fshuai.dto.*;
import com.fshuai.entity.Project;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.FileService;
import com.fshuai.service.ProjectService;
import com.fshuai.utils.AliOssUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/student/project")
@Api(tags = "学生项目相关接口")
@Slf4j
public class StudentProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    FileService fileService;

    @GetMapping("/page")
    @ApiOperation("查询自己参加的项目")
    public Result<PageResult> getMyProjects() {
        PageResult pageResult = projectService.getMyProject();
        return Result.success(pageResult);
    }

    @PostMapping("/apply")
    @ApiOperation("项目申报")
    public Result applyProject(@RequestBody ProjectApplyDTO projectApplyDTO) {
        projectService.addProject(projectApplyDTO);
        return Result.success();
    }

    @PostMapping("/review")
    @ApiOperation("提交审核报告")
    public Result applyProject(@RequestBody ProjectReviewApplyDTO reviewApplyDTO) {
        projectService.applyReview(reviewApplyDTO);
        return Result.success();
    }

    @PostMapping("/review/end")
    @ApiOperation("提交结项审核，需要额外提供材料")
    public Result applyCompletionProject(@RequestBody ProjectReviewApplyAchievementDTO attachmentsDTO) {
        // 检查结项项目，要额外处理材料
        projectService.applyCompletionReview(attachmentsDTO);
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

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result uploadFile(Integer projectId, MultipartFile file) {
        fileService.uploadFile(projectId,file);
        return Result.success();
    }

//    @PostMapping("/upload")
//    @ApiOperation("上传文件")
//    public Result uploadFile(Integer projectId,  File file) {
//        return Result.success();
//    }
}
