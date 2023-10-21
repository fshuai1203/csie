package com.fshuai.utils;

import com.fshuai.constant.PathConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class FileUtil {

    @Value("${document.uploadPath}")
    private  String uploadPath;

    public  String upload(MultipartFile file, String fileName) throws IOException {
        File saveFile = new File(uploadPath);
        // 父目录不存在则创建目录
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        file.transferTo(new File(uploadPath + fileName));
        StringBuilder stringBuilder = new StringBuilder(PathConstant.CLIENT_IMG_PTAH);
        stringBuilder.append("/").append(fileName);
        log.info("构建文件访问路径{}",stringBuilder.toString());
        return stringBuilder.toString();

    }

    public boolean delete(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/"));
        File file = new File(uploadPath+fileName);
        if (!file.exists()){
            log.error("删除的文件不存在");
        }
        return file.delete();
    }

}
