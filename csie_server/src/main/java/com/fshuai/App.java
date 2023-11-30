package com.fshuai;

import com.fshuai.properties.AliOssProperties;
import com.fshuai.utils.AliOssUtil;
import com.fshuai.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class App {

    @Autowired
    AliOssProperties aliOssProperties;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("server started");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(0, 0);
    }

    @Bean
    public AliOssUtil createAliOssUtil() {
        return new AliOssUtil(aliOssProperties.getEndpoint()
                , aliOssProperties.getAccessKeySecret()
                , aliOssProperties.getAccessKeyId()
                , aliOssProperties.getBucketName());
    }
}