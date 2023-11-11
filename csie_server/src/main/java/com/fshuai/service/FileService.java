package com.fshuai.service;

import com.fshuai.entity.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String uploadFile(MultipartFile file);

    List<Integer> updateProjectFile(Project project, List<String> attachments);

    void deleteFile(Project project);
}
