package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
import com.fshuai.context.StudentBaseContext;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectFile;
import com.fshuai.exception.StudentProjectException;
import com.fshuai.mapper.ProjectFileMapper;
import com.fshuai.mapper.ProjectMapper;
import com.fshuai.service.FileService;
import com.fshuai.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    AliOssUtil aliOssUtil;


    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectFileMapper projectFileMapper;

    static Map<Integer, String> fileNameMap = new HashMap<>();

    static Map<Integer, String> fileNameEnglishMap = new HashMap<>();

    FileServiceImpl() {
        fileNameMap.put(1, "立项审核材料");
        fileNameMap.put(3, "中期审核材料");
        fileNameMap.put(6, "结项审核材料");
        fileNameMap.put(10, "延期审核材料");
        fileNameMap.put(-1, "成员变更材料");
        fileNameMap.put(-2, "公告材料");
        fileNameMap.put(-3, "模版材料");

        fileNameEnglishMap.put(1, "approval_review");
        fileNameEnglishMap.put(3, "midterm_review");
        fileNameEnglishMap.put(6, "completion_review");
        fileNameEnglishMap.put(10, "postpone_review");
        fileNameEnglishMap.put(-1, "members_change");
        fileNameEnglishMap.put(-2, "announcement");
        fileNameEnglishMap.put(-3, "template");
    }

    /**
     * 上传项目文档
     * 包括立项，中期，结项
     *
     * @param projectId
     * @param file
     */
    @Override
    public void uploadFile(Integer projectId, MultipartFile file) {
        // 根据projectId检查项目，检查申请上传文件人和项目负责人是否一致
        Project project = checkProjectPrincipal(projectId);
        // 检查项目的状态是否和申请时的状态一致
        String fileName = fileNameMap.get(project.getState());
        // 检查文档是否为空
        if (file.isEmpty()) {
            throw new StudentProjectException(MessageConstant.FILE_ADD_FAILED_STATE_FALSE);
        }
        if (fileName == null) {
            throw new StudentProjectException(MessageConstant.FILE_ADD_FAILED_FILE_EMPTY);
        }
        Integer count = projectFileMapper.getCountLikeName(fileName);
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        ProjectFile projectFile = ProjectFile
                .builder()
                .state(project.getState())
                .name(project.getNumber() + fileName + (count + 1) + suffix)
                .build();
        String url = aliOssUtil.upload(file, project.getNumber() + "_" + fileNameEnglishMap.get(project.getState()) + (count + 1) + suffix);
        projectFile.setUrl(url);
        projectFileMapper.insert(projectFile);

    }


    /**
     * 检查项目负责人和项目申请人是否一致
     * 如果一致返回项目
     *
     * @param projectId
     */
    private Project checkProjectPrincipal(Integer projectId) {
        Integer studentId = StudentBaseContext.getCurrentId();
        Project project = projectMapper.selectById(projectId);
        if (project == null || project.getPrincipalId() != studentId) {
            throw new StudentProjectException(MessageConstant.FILE_ADD_FAILED_PRINCIPAL_NOT_MATCH);
        }
        return project;
    }
}
