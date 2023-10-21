package com.fshuai.mapper;

import com.fshuai.entity.ProjectFile;
import com.fshuai.vo.ProjectFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectFileMapper {
    void insertProjectFile(ProjectFile projectFile);


    void deleteLikeName(String name);

    List<ProjectFileVO> selectProjectFileVOByIds(String[] ids);
}
