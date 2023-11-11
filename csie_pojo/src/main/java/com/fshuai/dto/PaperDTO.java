package com.fshuai.dto;

import lombok.Data;

// 论文
@Data
class PaperDTO {

    // 论文名称
    private String paperName;
    // 发表刊物名称
    private String journalName;
    // 检索类型
    private String searchType;
    // 检索号
    private String searchNumber;
    // 第一作者
    private String firstAuthor;

    // 项目地址
    private String uri;

}
