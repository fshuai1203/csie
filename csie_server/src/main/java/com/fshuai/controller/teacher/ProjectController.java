package com.fshuai.controller.teacher;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.ProjectService;
import com.fshuai.vo.ProjectDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/teacher/project")
@Api(tags = "项目相关接口")
@Slf4j
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/page")
    @ApiOperation("项目查询")
    public Result<PageResult> getProjects(@RequestBody ProjectPageQueryDTO projectPageQueryDTO) {
        PageResult pageResult = projectService.pageQuery(projectPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/review/{state}")
    @ApiOperation("获取待审核的项目")
    public Result<PageResult> getProjects(@PathVariable Integer state) {
        PageResult pageResult = projectService.getReviewProjects(state);
        return Result.success(pageResult);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据id查询项目")
    public Result<ProjectDetailVO> getProjectDetail(@PathVariable Integer id) {
        ProjectDetailVO projectDetail = projectService.detail(id);
        return Result.success(projectDetail);
    }

    @PostMapping("/review")
    @ApiOperation("项目审核")
    public Result projectReview(@RequestBody ProjectReviewDTO projectReviewDTO) {
        projectService.review(projectReviewDTO);
        return Result.success();
    }

    @GetMapping("/finance")
    @ApiOperation("财务统计")
    public Result<PageResult> getProjectFinance(ProjectFinancePageDTO projectFinancePageDTO) {
        PageResult projectFinance = projectService.getProjectFinance(projectFinancePageDTO);
        return Result.success(projectFinance);
    }

    @PostMapping("/finance")
    @ApiOperation("财务审核")
    public Result<PageResult> projectFinanceReview(@RequestBody ProjectFinanceReviewDTO projectFinanceReviewDTO) {
        projectService.projectFinanceReview(projectFinanceReviewDTO);
        return Result.success();
    }


    @GetMapping("/attachments/{id}")
    @ApiOperation("根据项目id下载附件")
    public Result getProjectAttachments(HttpServletResponse response, @PathVariable Integer id) {
        projectService.getProjectAttachmentsById(response, id);
        return Result.success();
    }

    @GetMapping("/attachments")
    @ApiOperation("根据获取具体格式的附件")
    public Result getProjectAttachments(HttpServletResponse response, @RequestBody AttachmentPageDTO attachmentPageDTO) {
        projectService.getProjectAttachments(response, attachmentPageDTO);
        return Result.success();
    }

}
