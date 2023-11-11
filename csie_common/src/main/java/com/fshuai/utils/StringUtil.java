package com.fshuai.utils;

import com.fshuai.constant.PathConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class StringUtil {

    public String IntListTOString(List<Integer> integers) {
        if (integers == null || integers.isEmpty()) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(integers.get(0));
        for (int i = 1; i < integers.size(); i++) {
            buffer.append(",");
            buffer.append(integers.get(i));
        }
        return buffer.toString();
    }

}
