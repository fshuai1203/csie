package com.fshuai.mapper;

import com.fshuai.dto.ProjectPageQueryDTO;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectFile;
import com.fshuai.vo.ProjectPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectFileMapper {
    Integer getCountLikeName(String fileName);

    void insert(ProjectFile projectFile);
}
