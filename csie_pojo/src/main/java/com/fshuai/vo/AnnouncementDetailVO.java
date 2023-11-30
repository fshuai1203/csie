package com.fshuai.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnnouncementDetailVO {

    private Integer id;
    private String title;
    private String content;
    private List<ProjectFileVO> attachments;
    private LocalDate publishDate;
    private String deptName;

}
