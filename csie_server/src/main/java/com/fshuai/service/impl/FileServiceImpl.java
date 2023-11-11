package com.fshuai.service.impl;

import com.fshuai.constant.MessageConstant;
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

import java.util.*;

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
        fileNameMap.put(4, "中期审核材料");
        fileNameMap.put(7, "结项审核材料");
        fileNameMap.put(10, "延期审核材料");
        fileNameMap.put(-1, "成员变更材料");
        fileNameMap.put(-2, "公告材料");
        fileNameMap.put(-3, "模版材料");

        fileNameEnglishMap.put(1, "approval_review");
        fileNameEnglishMap.put(4, "midterm_review");
        fileNameEnglishMap.put(7, "completion_review");
        fileNameEnglishMap.put(10, "postpone_review");
        fileNameEnglishMap.put(-1, "members_change");
        fileNameEnglishMap.put(-2, "announcement");
        fileNameEnglishMap.put(-3, "template");
    }

    /**
     * 上传项目文档
     * 包括立项，中期，结项
     *
     * @param file
     */
    @Override
    public String uploadFile(MultipartFile file) {
        // 检查文档是否为空
        if (file.isEmpty()) {
            throw new StudentProjectException(MessageConstant.FILE_ADD_FAILED_STATE_FALSE);
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = aliOssUtil.upload(file, UUID.randomUUID() + suffix);
        return url;
    }

    /**
     * 将项目附件插入到projectfile表中
     *
     * @param project
     * @param attachments
     * @return 每一个文件的id
     */
    @Override
    public List<Integer> updateProjectFile(Project project, List<String> attachments) {
        // 检查项目的状态是否和申请时的状态一致
        String fileName = fileNameMap.get(project.getState());
        if (attachments == null || attachments.isEmpty()) {
            return null;
        }
        if (fileName == null) {
            throw new StudentProjectException(MessageConstant.STATE_FAILED);
        }
        List<Integer> fileIds = new ArrayList();
        int count = 1;
        for (String attachment : attachments) {
            String suffix = attachment.substring(attachment.lastIndexOf("."));
            ProjectFile projectFile = ProjectFile
                    .builder()
                    .state(project.getState())
                    .name(project.getNumber() + fileName + count + suffix)
                    .url(attachment)
                    .build();
            count++;
            projectFileMapper.insertProjectFile(projectFile);
            fileIds.add(projectFile.getId());
        }
        return fileIds;
    }

    @Override
    public void deleteFile(Project project) {
        String fileName = fileNameMap.get(project.getState());
        projectFileMapper.deleteLikeName(project.getNumber() + fileName);
        //:TODO 删除阿里云中的文件
    }


}
