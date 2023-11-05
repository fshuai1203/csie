package com.fshuai.service;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;
import com.fshuai.vo.ProjectDetailVO;

import javax.servlet.http.HttpServletResponse;

public interface ProjectService {
    PageResult pageQuery(ProjectPageQueryDTO projectPageQueryDTO);

    ProjectDetailVO detail(Integer id);

    void review(ProjectReviewDTO projectReviewDTO);

    PageResult getProjectFinance(ProjectFinancePageDTO projectFinancePageDTO);

    void getProjectAttachmentsById(HttpServletResponse response, Integer id);

    void getProjectAttachments(HttpServletResponse response, AttachmentPageDTO attachmentPageDTO);

    PageResult getMyProject();

    void addProject(ProjectApplyDTO projectApplyDTO);

    PageResult getReviewProjects(ProjectReviewPageQueryDTO pageQueryDTO);

    void applyReview(ProjectReviewApplyDTO reviewApplyDTO);

    void updateMember(ProjectMemberDTO projectMemberDTO);


    void addProjectFinance(ProjectFinanceDTO projectFinanceDTO);

    void projectFinanceReview(ProjectFinanceReviewDTO projectFinanceReviewDTO);

    void applyCompletionReview(ProjectReviewApplyAchievementDTO attachmentsDTO);
}
