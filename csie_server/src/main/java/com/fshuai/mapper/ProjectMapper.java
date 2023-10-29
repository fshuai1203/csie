package com.fshuai.mapper;

import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectDeadline;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    void insert(Project project);

    List<Project> selectByPrincipalId(Integer principalId);

    List<Project> selectByIds(List<Integer> projectIds);
    Project selectById(Integer projectId);
}
