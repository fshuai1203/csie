package com.fshuai.service.impl;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;
import com.fshuai.service.ProjectService;
import com.fshuai.vo.ProjectDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Override
    public PageResult pageQuery(ProjectPageQueryDTO projectPageQueryDTO) {
        return null;
    }

    @Override
    public ProjectDetailVO detail(Integer id) {
        return null;
    }

    @Override
    public void review(ProjectReviewDTO projectReviewDTO) {

    }

    @Override
    public void getProjectFinance(ProjectFinancePageDTO projectFinancePageDTO) {

    }

    @Override
    public void getProjectAttachmentsById(HttpServletResponse response, Integer id) {

    }

    @Override
    public void getProjectAttachments(HttpServletResponse response, AttachmentPageDTO attachmentPageDTO) {

    }

    @Override
    public PageResult getMyProject() {
        return null;
    }

    @Override
    public void addProject(ProjectDTO projectDTO) {

    }

    @Override
    public PageResult getReviewProjects(Integer state) {
        return null;
    }

    @Override
    public void reviewProject(ProjectReviewAttachmentsDTO attachmentsDTO) {

    }

    @Override
    public void updateMember(ProjectMemberDTO projectMemberDTO) {

    }

    @Override
    public void addProjectFinance(ProjectFinanceDTO projectFinanceDTO) {

    }
}
