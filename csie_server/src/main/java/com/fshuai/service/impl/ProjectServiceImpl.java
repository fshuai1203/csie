package com.fshuai.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fshuai.constant.*;
import com.fshuai.context.StudentBaseContext;
import com.fshuai.context.TeacherBaseContext;
import com.fshuai.dto.*;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectFinance;
import com.fshuai.entity.ProjectReview;
import com.fshuai.exception.AnnouncementNotAllowedException;
import com.fshuai.exception.StudentProjectException;
import com.fshuai.exception.TeacherProjectException;
import com.fshuai.mapper.*;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.service.FileService;
import com.fshuai.service.ProjectService;
import com.fshuai.utils.IdWorker;
import com.fshuai.utils.StringUtil;
import com.fshuai.vo.ProjectDetailVO;
import com.fshuai.vo.ProjectPageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
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
    FileService fileService;


    @Autowired
    ProjectFinanceMapper projectFinanceMapper;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    ProjectReviewMapper projectReviewMapper;

    @Autowired
    StringUtil stringUtil;

    /**
     * 教师端查询项目
     *
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ProjectPageQueryDTO projectPageQueryDTO) {
        // 检查老师权限
        Map map = checkTeacherRole();
        Integer teacherRole = getTeacherRoleByMap(map);
        Integer teacherDept = getTeacherDeptByMap(map);
        // 系级负责人只能查看本系的项目
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD) {
            projectPageQueryDTO.setDeptId(teacherDept);
        }
        // 根据projectPageQueryDTO查询项目
        PageHelper.startPage(projectPageQueryDTO.getPage(), projectPageQueryDTO.getPageSize());
        Page<ProjectPageVO> page = projectMapper.pageQuery(projectPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 教师端根据Id获取项目详细信息
     *
     * @param id
     * @return
     */
    @Override
    public ProjectDetailVO detail(Integer id) {
        Project project = checkProjectIsNotNull(id);
        ProjectDetailVO projectDetailVO = ProjectDetailVO.builder().build();
        BeanUtils.copyProperties(project, projectDetailVO);
        // 补全负责人信息以及小组成员信息
        // 补全负责人
        projectDetailVO.setPrincipal(studentMapper.selectStudentVoById(project.getPrincipalId()));
        // 补全小组成员
        List<Integer> memberIds = projectMemberMapper.selectMemberIdsByProjectId(id);
        projectDetailVO.setProjectMembers(studentMapper.selectStudentVoByIds(memberIds));
        return projectDetailVO;
    }

    /**
     * 教师审核
     *
     * @param projectReviewDTO
     */
    @Override
    public void review(ProjectReviewDTO projectReviewDTO) {
        //:todo 利用切片检查字段
        // 检查教师权限
        Map map = checkTeacherRole();
        // 检查项目Id是否正确
        Project project = checkProjectIsNotNull(projectReviewDTO.getProjectId());
        // 检查是否是系级教师是否是审核了自己系的项目,校级教师不用审核
        Integer teacherRole = getTeacherRoleByMap(map);
        Integer teacherDept = getTeacherDeptByMap(map);
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD && teacherDept != project.getDeptId()) {
            throw new TeacherProjectException(MessageConstant.ROLE_FAILED);
        }
        // 进行项目审核，所提交的state要比project中的state大1或2
        // 审核通过
        // 如果同意approval为true，state大2，否则错误
        // 获取项目状态
        Integer oldState = project.getState();
        Integer newState = projectReviewDTO.getState();
        Project updateProject = Project
                .builder()
                .id(project.getId())
                .build();
        if (projectReviewDTO.isApproval() && newState - oldState == 2) {
            // 获取对应项目待审核记录
            ProjectReview projectReview = projectReviewMapper.selectByProjectIdAndState(projectReviewDTO.getProjectId(), oldState);
            // 更新ProjectReview
            BeanUtils.copyProperties(projectReviewDTO, projectReview);
            projectReview.setDate(LocalDate.now());
            projectReviewMapper.updateProjectReview(projectReview);
            updateProject.setState(oldState + 2);
            if (projectReview.getAttachments() != null) {
                updateProject.setAttachments(projectReview.getAttachments() + "," + project.getAttachments());
            }
        }
        // 审核失败
        // 如果不同意approval为false，state大1，否则错误
        // 审核失败需要重新提交材料，因此project的state由提交材料待审核变为待提交材料
        else if (!projectReviewDTO.isApproval() && newState - oldState == 1) {
            // 获取对应项目待审核记录
            ProjectReview projectReview = projectReviewMapper.selectByProjectIdAndState(projectReviewDTO.getProjectId(), oldState);
            // 更新ProjectReview
            BeanUtils.copyProperties(projectReviewDTO, projectReview);
            projectReview.setDate(LocalDate.now());
            projectReviewMapper.updateProjectReview(projectReview);
            updateProject.setState(oldState - 1);
            //删除相关材料以及projectFile的记录
            fileService.deleteFile(project);

        } else {
            throw new TeacherProjectException(MessageConstant.PROJECT_REVIEW_FAILED_STATE_NOT_MATCH);
        }
        // 更新project中的state，如果失败，则state减1（待提交材料），如果成功state加2（进入下一个状态）
        // 更新Project
        projectMapper.update(updateProject);
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
     * 教师根据项目状态获取相应的项目
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getReviewProjects(ProjectReviewPageQueryDTO pageQueryDTO) {
        // 检查教师权限
        Map map = checkTeacherRole();
        Integer teacherRole = getTeacherRoleByMap(map);
        Integer teacherDept = getTeacherDeptByMap(map);
        // 系级负责人只能获取本系的项目
        // 校级负责人可以获取所有的项目
        // 将states转换为数组
        List<String> statesList = Arrays.asList(pageQueryDTO.getStates().split(","));
        List<Integer> states = statesList.stream().map(Integer::valueOf).collect(Collectors.toList());

        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Page<ProjectPageVO> projectFinances = projectMapper.
                reviewPageQuery(states, teacherRole == RoleConstant.SCHOOL_HEAD ? null : teacherDept);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(projectFinances);
        pageResult.setTotal(projectFinances.size());
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
        insertProjectReview(project, projectApplyDTO.getAttachments());

    }

    /**
     * 把项目和相应的附件进行绑定
     *
     * @param project
     * @param attachments
     */
    private void insertProjectReview(Project project, List<String> attachments) {
        // 将文件保存到file
        List<Integer> ids = fileService.updateProjectFile(project, attachments);
        // 将提交记录保存到project_review中
        ProjectReview projectReview = ProjectReview
                .builder()
                .projectId(project.getId())
                .state(project.getState())
                .attachments(stringUtil.IntListTOString(ids))
                .build();
        projectReviewMapper.insert(projectReview);
    }

    /**
     * 学生提交审核报告
     *
     * @param reviewApplyDTO
     */
    @Override
    public void applyReview(ProjectReviewApplyDTO reviewApplyDTO) {
        // 根据项目Id检查项目状态,并检查项目负责人Id和申请人Id
        Project project = checkProjectPrincipal(reviewApplyDTO.getProjectId());
        // 检查项目状态与所提交的状态是否一致，从而确保申请人所提交材料正确
        if (project.getState() != reviewApplyDTO.getState()) {
            throw new StudentProjectException(MessageConstant.STATE_FAILED);
        }
        // 更新项目状态为待检查状态，并将本次提交保存到project_review中记录
        // 更新project的state为提交材料待审核
        project.setState(project.getState() + 1);
        reviewApplyDTO.setState(project.getState() + 1);
        insertProjectReview(project, reviewApplyDTO.getAttachments());

        // 更新项目表中的项目状态
        // 材料提交成功，由待提交材料状态转变为材料提交成功，待审核
        Project updataProject = Project
                .builder()
                .id(project.getId())
                .state(project.getState())
                .build();
        projectMapper.update(updataProject);
    }

    /**
     * 学生结项审核，需要额外提交材料, 其余和申请审核一致
     *
     * @param achievementDTO
     */
    @Override
    public void applyCompletionReview(ProjectReviewApplyAchievementDTO achievementDTO) {
        // 审核报告
        ProjectReviewApplyDTO applyDTO = new ProjectReviewApplyDTO();
        BeanUtils.copyProperties(achievementDTO, applyDTO);
        applyReview(applyDTO);
        // 处理attachmentsDTO，将其转换为JSON格式的string文件进行存储
        // 使用jackson进行转换
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String achievement = objectMapper.writeValueAsString(achievementDTO.getAchievement());
            Project project = Project
                    .builder()
                    .id(achievementDTO.getProjectId())
                    .achievement(achievement)
                    .build();
            projectMapper.update(project);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
     * 根据项目Id获取附件
     *
     * @param response
     * @param id
     */
    @Override
    public void getProjectAttachmentsById(HttpServletResponse response, Integer id) {
        //:todo 根据项目Id获取附件
    }

    /**
     * 根据附件条件获取一定数量的附件
     *
     * @param response
     * @param attachmentPageDTO
     */
    @Override
    public void getProjectAttachments(HttpServletResponse response, AttachmentPageDTO attachmentPageDTO) {
        //:todo 根据附件条件获取一定数量的附件
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

    /**
     * 检查教师是否是管理员
     *
     * @return 教师信息Map
     */
    private Map checkTeacherRole() {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有负责人才能更改此项
        if (teacherRole != RoleConstant.SCHOOL_HEAD && teacherRole != RoleConstant.DEPARTMENT_HEAD) {
            throw new AnnouncementNotAllowedException(MessageConstant.ROLE_FAILED);
        }
        return teacherMap;
    }

    private Project checkProjectIsNotNull(Integer projectId) {
        Project project = projectMapper.selectById(projectId);
        // 如果项目为空则说明projectId错误
        if (project == null) {
            throw new TeacherProjectException(MessageConstant.PROJECT_ID_FAILED);
        }
        return project;
    }

    private Integer getTeacherRoleByMap(Map map) {
        return (Integer) map.get(jwtProperties.getTeacherRoleKey());
    }

    private Integer getTeacherIdByMap(Map map) {
        return (Integer) map.get(jwtProperties.getTeacherIdKey());
    }

    private Integer getTeacherDeptByMap(Map map) {
        return (Integer) map.get(jwtProperties.getTeacherDeptKey());
    }
}
