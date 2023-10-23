package com.fshuai.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fshuai.jwt")
@Data
public class JwtProperties {

    /**
     * 教师端生成jwt令牌相关配置
     */
    private String teacherSecretKey;
    private long teacherTtl;
    private String teacherTokenName;
    private String teacherIdKey = "teacherId";
    private String teacherRoleKey = "teacherRole";
    private String teacherDeptKey = "teacherDept";


    /**
     * 学生端端微信用户生成jwt令牌相关配置
     */
    private String studentSecretKey;
    private long studentTtl;
    private String studentTokenName;

}
