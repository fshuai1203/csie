package com.fshuai.dto;


import lombok.Data;

// 项目成果
@Data
public class AchievementDTO {

    private Page page;

    private String content;

    private String attachments;

}

class Page {

    private String pageName;
    private String journalName;

    private String searchType;

    private String searchNumber;

    private String firstAuthor;

}

class Patent {
    private String patentName;

    private String AuthorizationDate;

    private String patentNumber;

}

class PhysicalProject {
    private String projectName;

    private String firstAuthor;

}
