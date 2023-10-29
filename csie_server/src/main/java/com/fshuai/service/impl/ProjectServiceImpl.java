package com.fshuai.service.impl;

import com.fshuai.constant.*;
import com.fshuai.context.StudentBaseContext;
import com.fshuai.context.TeacherBaseContext;
import com.fshuai.dto.*;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectFinance;
import com.fshuai.exception.StudentProjectException;
import com.fshuai.exception.TeacherProjectException;
import com.fshuai.mapper.ProjectFinanceMapper;
import com.fshuai.mapper.ProjectMapper;
import com.fshuai.mapper.ProjectMemberMapper;
import com.fshuai.mapper.StudentMapper;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.service.ProjectService;
import com.fshuai.utils.IdWorker;
import com.fshuai.vo.ProjectDetailVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    IdWorker idWorker;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectMemberMapper projectMemberMapper;

    @Autowired
    ProjectFinanceMapper projectFinanceMapper;

    @Autowired
    JwtProperties jwtProperties;

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

    /**
     * 教师端查看项目报销申请
     * 校级负责人可以查看全部报销记录
     * 系级负责人只能查看本系的报销（需求不明确）
     *
     * @param projectFinancePageDTO
     */
    @Override
    public PageResult getProjectFinance(ProjectFinancePageDTO projectFinancePageDTO) {
        checkFinanceRole();
        PageHelper.startPage(projectFinancePageDTO.getPage(), projectFinancePageDTO.getPageSize());
        Page<ProjectFinance> projectFinances = projectFinanceMapper.pageQuery(projectFinancePageDTO);
        return new PageResult(projectFinances.getTotal(), projectFinances.getResult());
    }

    /**
     * 财务报销审核
     *
     * @param projectFinanceReviewDTO
     */
    @Override
    public void projectFinanceReview(ProjectFinanceReviewDTO projectFinanceReviewDTO) {
        Integer teacherId = checkFinanceRole();
        // 检查是否有相应的报销单号
        ProjectFinance projectFinance = projectFinanceMapper.selectByProjectIdAndFinanceNumber(projectFinanceReviewDTO.getProjectId(), projectFinanceReviewDTO.getFinanceNumber());
        if (projectFinance == null) {
            throw new TeacherProjectException(MessageConstant.FINANCE_UPDATE_FAILED_ID_NUMBER_NOT_MATCH);
        }
        BeanUtils.copyProperties(projectFinanceReviewDTO, projectFinance);
        projectFinance.setProcessor(teacherId);
        projectFinanceMapper.update(projectFinance);
    }

    /**
     * 检查获财务报销人的身份，只有校级负责人才可以获得报销
     */
    private Integer checkFinanceRole() {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        Integer teacherId = (Integer) teacherMap.get(jwtProperties.getTeacherIdKey());
        // 校级负责人可以查看所有报销记录
        if (teacherRole != RoleConstant.SCHOOL_HEAD) {
            throw new TeacherProjectException(MessageConstant.ROLE_FAILED);
        }
        return teacherId;
    }

    @Override
    public void getProjectAttachmentsById(HttpServletResponse response, Integer id) {

    }

    @Override
    public void getProjectAttachments(HttpServletResponse response, AttachmentPageDTO attachmentPageDTO) {

    }

    /**
     * 学生查询自己参加到项目，包括负责人和项目成员到项目
     *
     * @return
     */
    @Override
    public PageResult getMyProject() {
        // 获取学生ID
        Integer studentID = StudentBaseContext.getCurrentId();
        // 获取学生担任负责人的项目
        List<Project> projects = projectMapper.selectByPrincipalId(studentID);
        // 检查学生担任组内成员的项目
        List<Integer> projectIds = projectMemberMapper.selectProjectIdsByMemberId(studentID);
        if (projectIds.size() != 0) {
            projects.addAll(projectMapper.selectByIds(projectIds));
        }
        PageResult pageResult = new PageResult();
        pageResult.setRecords(projects);
        pageResult.setTotal(projects.size());
        return pageResult;
    }


    /**
     * 学生添加自己的项目
     * 必须是负责人才能添加项目
     *
     * @param projectApplyDTO
     */
    @Override
    public void addProject(ProjectApplyDTO projectApplyDTO) {
        // 检查项目申请人是否在项目中担任负责人
        // 获取学生ID
        Integer studentID = StudentBaseContext.getCurrentId();
        // 检查负责人ID和学生ID是否匹配
        if (projectApplyDTO.getPrincipal() == null || projectApplyDTO.getPrincipal().getId() != studentID) {
            throw new StudentProjectException(MessageConstant.PROJECT_ADD_FAILED_PRINCIPAL_NOT_MATCH);
        }
        //:todo 检查各个字段是否为空,未来可以考虑用切面实现
        // 检查category
        Integer category = projectApplyDTO.getCategory();
        if (category != CategoryConstant.INNOVATE && category != CategoryConstant.BUSINESS) {
            throw new StudentProjectException(MessageConstant.PROJECT_ADD_FAILED_CATEGORY_NOT_TRUE);
        }
        // 创建项目
        Project project = Project
                .builder()
                .principalId(projectApplyDTO.getPrincipal().getId())
                .number(String.valueOf(idWorker.nextId()))//为项目生成一个随机编号
                .type(TypeConstant.DEPT_TYPE) // 默认是系级项目
                .state(ProjectStateConstant.APPROVAL_WAIT_APPLY)
                .build();
        BeanUtils.copyProperties(projectApplyDTO, project);
        // 添加project到project表中
        projectMapper.insert(project);
        // 添加project到project_member表中
        // 获取项目ID
        Integer projectId = project.getId();
        List<MemberDTO> members = projectApplyDTO.getMembers();
        // 插入项目成员
        projectMemberMapper.insertByMembersAndProjectId(members, projectId);

    }

    @Override
    public PageResult getReviewProjects(Integer state) {
        return null;
    }

    /**
     * 提交审核报告
     *
     * @param attachmentsDTO
     */
    @Override
    public void reviewProject(ProjectReviewAttachmentsDTO attachmentsDTO) {

    }

    /**
     * 更改项目成员
     * 只有负责人才能够更改项目成员
     *
     * @param projectMemberDTO
     */
    @Override
    public void updateMember(ProjectMemberDTO projectMemberDTO) {
        Integer projectId = projectMemberDTO.getProjectId();
        Project project = checkProjectPrincipal(projectId);
        // 删除之前项目的成员
        projectMemberMapper.deleteByProjectId(projectId);
        // 添加修改后项目的成员
        projectMemberMapper.insertByMembersAndProjectId(projectMemberDTO.getMembers(), projectId);
    }

    /**
     * 申请项目报销
     * 只有负责人能申请,申请人就是负责人
     * 操作人是确认报销的老师
     *
     * @param projectFinanceDTO
     */
    @Override
    public void addProjectFinance(ProjectFinanceDTO projectFinanceDTO) {
        Integer projectId = projectFinanceDTO.getProjectId();
        Project project = checkProjectPrincipal(projectId);
        //:todo 检查项目的状态
        ProjectFinance projectFinance = ProjectFinance
                .builder()
                .projectId(projectId)
                .projectName(project.getName())
                .type(project.getType())
                .deptId(project.getDeptId())
                .principalName(project.getPrincipalName())
                .price(projectFinanceDTO.getPrice())
                .applyTime(LocalDate.now())
                .financeNumber(String.valueOf(idWorker.nextId()))
                .attachments(project.getAttachments())
                .state(FinanceStateConstant.WAIT_REVIEWED)
                .build();
        projectFinanceMapper.insert(projectFinance);
    }


    /**
     * 检查项目负责人和项目申请人是否一致
     * 如果一致返回项目
     *
     * @param projectId
     */
    private Project checkProjectPrincipal(Integer projectId) {
        Integer studentId = StudentBaseContext.getCurrentId();
        Project project = projectMapper.selectById(projectId);
        if (project == null || project.getPrincipalId() != studentId) {
            throw new StudentProjectException(MessageConstant.FINANCE_ADD_FAILED_PRINCIPAL_NOT_MATCH);
        }
        return project;
    }
}
