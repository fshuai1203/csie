package com.fshuai.mapper;

import com.fshuai.dto.ProjectPageQueryDTO;
import com.fshuai.dto.ProjectReviewPageQueryDTO;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectDeadline;
import com.fshuai.entity.ProjectFinance;
import com.fshuai.vo.ProjectPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    void insert(Project project);

    List<Project> selectByPrincipalId(Integer principalId);

    List<Project> selectByIds(List<Integer> projectIds);

    Project selectById(Integer projectId);

    Page<ProjectPageVO> pageQuery(ProjectPageQueryDTO projectPageQueryDTO);

    Page<ProjectPageVO> reviewPageQuery(List<Integer> states, Integer deptId);

    void update(Project project);
}
