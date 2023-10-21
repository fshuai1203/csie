package com.fshuai.service;

import com.fshuai.entity.Project;
import com.fshuai.vo.ProjectFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String uploadFile(MultipartFile file);

    String insertProjectFile(Project project, List<String> attachments);

    String insertFile(String name, Integer state, List<String> attachments);

    void deleteFile(Project project);

    List<ProjectFileVO> getProjectFileByIds(String attachments);

}
