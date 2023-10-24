package com.fshuai.service.impl;

import com.fshuai.constant.DeptConstant;
import com.fshuai.constant.MessageConstant;
import com.fshuai.constant.RoleConstant;
import com.fshuai.constant.StatusConstant;
import com.fshuai.context.TeacherBaseContext;
import com.fshuai.dto.AnnouncementDTO;
import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.dto.AnnouncementUpdateDTO;
import com.fshuai.entity.Announcement;
import com.fshuai.exception.AnnouncementNotAllowedException;
import com.fshuai.exception.DeletionNotAllowedException;
import com.fshuai.mapper.AnnouncementMapper;
import com.fshuai.properties.JwtProperties;
import com.fshuai.result.PageResult;
import com.fshuai.vo.AnnouncementVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class AnnouncementServiceImpl implements com.fshuai.service.AnnouncementService {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    AnnouncementMapper announcementMapper;

    /**
     * @param announcementPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(AnnouncementPageQueryDTO announcementPageQueryDTO) {
        PageHelper.startPage(announcementPageQueryDTO.getPage(), announcementPageQueryDTO.getPageSize());
        Page<AnnouncementVO> page = announcementMapper.pageQuery(announcementPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 更新公告
     *
     * @param announcementUpdateDTO
     */
    @Override
    public void update(AnnouncementUpdateDTO announcementUpdateDTO) {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 校级负责人可以修改所有公告
        if (teacherRole == RoleConstant.SCHOOL_HEAD) {
            announcementMapper.updateById(announcementUpdateDTO);
            return;
        }
        // 系级负责人只能修改本系的公告
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD) {
            List<Integer> ids = new ArrayList<>();
            ids.add(announcementUpdateDTO.getId());
            // 检查该公告是否是校级,如果为空则说明不是校级，则要修改
            if (announcementMapper.selectByDeptAndIds(DeptConstant.SCHOOL, ids).isEmpty()) {
                announcementMapper.deleteBatchByIds(ids);
                return;
            }
        }
        throw new DeletionNotAllowedException(MessageConstant.ROLE_FAILED);
    }

    /**
     * 添加公告
     * 校级负责人可以添加校级的公告
     * 系级负责人只能添加本系的公告
     *
     * @param announcementDTO
     */
    @Override
    public void add(AnnouncementDTO announcementDTO) {
        log.info("添加公告{}", announcementDTO);
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        Integer teacherId = (Integer) teacherMap.get(jwtProperties.getTeacherIdKey());
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 只有负责人才能发布公告
        if (teacherRole != RoleConstant.SCHOOL_HEAD && teacherRole != RoleConstant.DEPARTMENT_HEAD) {
            throw new AnnouncementNotAllowedException(MessageConstant.ROLE_FAILED);
        }

        Announcement announcement = Announcement
                .builder()
                .publishDate(LocalDate.now())
                .state(StatusConstant.ENABLE)
                .publishUser(teacherId)
                .deptId(teacherDept)
                .build();
        BeanUtils.copyProperties(announcementDTO, announcement);
        announcementMapper.insert(announcement);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        // 获取当前用户的权限
        Map teacherMap = TeacherBaseContext.getCurrentTeacher();
        Integer teacherDept = (Integer) teacherMap.get(jwtProperties.getTeacherDeptKey());
        // 获取当前用户的身份
        Integer teacherRole = (Integer) teacherMap.get(jwtProperties.getTeacherRoleKey());
        // 校级负责人可以删除所有公告
        if (teacherRole == RoleConstant.SCHOOL_HEAD) {
            announcementMapper.deleteBatchByIds(ids);
            return;
        }
        // 系级负责人只能删除本系的公告
        if (teacherRole == RoleConstant.DEPARTMENT_HEAD) {
            // 检查ids里面的公告有没有校级的
            // 如果为空则说明没有校级的，可以正常删除
            if (announcementMapper.selectByDeptAndIds(DeptConstant.SCHOOL, ids).isEmpty()) {
                announcementMapper.deleteBatchByIds(ids);
                return;
            }
        }
        throw new DeletionNotAllowedException(MessageConstant.ROLE_FAILED);
    }
}
