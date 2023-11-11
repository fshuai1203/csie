package com.fshuai.mapper;

import com.fshuai.entity.ProjectFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectFileMapper {
    void insertProjectFile(ProjectFile projectFile);


    void deleteLikeName(String name);
}
