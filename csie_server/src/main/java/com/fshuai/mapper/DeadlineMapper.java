package com.fshuai.mapper;

import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.ProjectDeadline;
import com.fshuai.entity.Teacher;
import com.fshuai.vo.TeacherVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeadlineMapper {
    void add(ProjectDeadline projectDeadline);

    ProjectDeadline selectById(String deadlineId);

    void update(ProjectDeadline projectDeadline);
}
