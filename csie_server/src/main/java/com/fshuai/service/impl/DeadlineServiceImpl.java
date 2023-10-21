package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
import com.fshuai.constant.RedisKeyConstant;
import com.fshuai.constant.RoleConstant;
import com.fshuai.constant.SettingsConstant;
import com.fshuai.context.TeacherBaseContext;
import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.dto.ProjectDeadlineUpdateDTO;
import com.fshuai.entity.ProjectDeadline;
import com.fshuai.entity.Settings;
import com.fshuai.exception.DeadlineException;
import com.fshuai.exception.TeacherUpdateFailedException;
import com.fshuai.mapper.DeadlineMapper;
import com.fshuai.mapper.SettingsMapper;
import com.fshuai.properties.JwtProperties;
import com.fshuai.service.DeadlineService;
import com.fshuai.vo.ProjectDeadlineVO;
import com.github.pagehelper.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@Transactional
public class DeadlineServiceImpl implements DeadlineService {

    @Autowired
    private DeadlineMapper deadlineMapper;

    @Autowired
    private SettingsMapper settingsMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ProjectDeadlineVO getDeadline() {
        // 获取当前deadline_id
        Settings settings = settingsMapper.selectByKey(SettingsConstant.DEADLINE_ID);
        if (settings == null) {
            throw new DeadlineException(MessageConstant.DEADLINE_NOT_INSERT);
        }
        String deadlineId = settings.getValue();
        ProjectDeadline projectDeadline = deadlineMapper.selectById(deadlineId);
        ProjectDeadlineVO projectDeadlineVO = ProjectDeadlineVO.builder().build();
        BeanUtils.copyProperties(projectDeadline, projectDeadlineVO);
        return projectDeadlineVO;
    }

    @Override
    public void updateDeadLine(ProjectDeadlineUpdateDTO projectDeadlineUpdateDTO) {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有校级负责人可以修改时间线
        if (teacherRole != RoleConstant.SCHOOL_HEAD) {
            throw new DeadlineException(MessageConstant.ROLE_FAILED);
        }
        // 获取当前deadline_id
        Settings settings = settingsMapper.selectByKey(SettingsConstant.DEADLINE_ID);
        if (settings == null) {
            throw new DeadlineException(MessageConstant.DEADLINE_NOT_INSERT);
        }
        String deadlineId = settings.getValue();
        ProjectDeadline projectDeadline = ProjectDeadline
                .builder()
                .deadlineId(deadlineId)
                .build();
        BeanUtils.copyProperties(projectDeadlineUpdateDTO, projectDeadline);
        deadlineMapper.update(projectDeadline);
        updateDeadLineCache();
    }

    /**
     * 添加时间线
     * 添加时间线意味着开启一条新的时间线
     * 数据库会保存上一条时间线
     * 添加时间线会在settings里面添加一个新的csi_id
     * 然后在对应csi_id,添加时间线
     * 在redis中添加每个阶段结束的时间
     *
     * @param projectDeadlineDTO
     */
    @Override
    public void addDeadLine(ProjectDeadlineDTO projectDeadlineDTO) {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有校级负责人可以添加时间线
        if (teacherRole != RoleConstant.SCHOOL_HEAD) {
            throw new DeadlineException(MessageConstant.ROLE_FAILED);
        }
        // 检查各个字段是否为空
        if (projectDeadlineDTO.getApprovalBeginDate() == null
                || projectDeadlineDTO.getApprovalEndDate() == null
                || projectDeadlineDTO.getMidtermBeginDate() == null
                || projectDeadlineDTO.getMidtermEndDate() == null
                || projectDeadlineDTO.getCompletionBeginDate() == null
                || projectDeadlineDTO.getCompletionEndDate() == null
        ) {
            throw new DeadlineException(MessageConstant.PROJECT_DEADLINE_FIELD_EMPTY);
        }

        // 根据日期产生新的编号
        String deadlineId = String.valueOf(LocalDate.now());
        // 更新deadline_id
        settingsMapper.deleteByKey(SettingsConstant.DEADLINE_ID);
        settingsMapper.add(SettingsConstant.DEADLINE_ID, deadlineId);
        // 将相应的时间线存入deadline中
        ProjectDeadline projectDeadline = ProjectDeadline.builder().build();
        projectDeadline.setDeadlineId(deadlineId);
        BeanUtils.copyProperties(projectDeadlineDTO, projectDeadline);
        deadlineMapper.add(projectDeadline);
        updateDeadLineCache();
    }

    /**
     * 更新redis缓存
     */
    private void updateDeadLineCache() {
        //获取时间线
        ProjectDeadlineVO deadline = getDeadline();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(RedisKeyConstant.APPROVAL_BEGIN_DATE, deadline.getApprovalBeginDate());
        valueOperations.set(RedisKeyConstant.APPROVAL_END_DATE, deadline.getApprovalEndDate());
        valueOperations.set(RedisKeyConstant.MIDTERM_BEGIN_DATE, deadline.getMidtermBeginDate());
        valueOperations.set(RedisKeyConstant.MIDTERM_END_DATE, deadline.getMidtermEndDate());
        valueOperations.set(RedisKeyConstant.COMPLETION_BEGIN_DATE, deadline.getCompletionBeginDate());
        valueOperations.set(RedisKeyConstant.COMPLETION_END_DATE, deadline.getCompletionEndDate());
        if (deadline.getPostponeBeginDate() != null) {
            valueOperations.set(RedisKeyConstant.POSTPONE_BEGIN_DATE, deadline.getPostponeBeginDate());
        }
        if (deadline.getPostponeEndDate() != null) {
            valueOperations.set(RedisKeyConstant.POSTPONE_END_DATE, deadline.getPostponeEndDate());
        }
    }
}
