package com.fshuai.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(Integer projectId, MultipartFile file);
}
