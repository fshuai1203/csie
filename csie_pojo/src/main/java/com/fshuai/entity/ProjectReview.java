package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReview {

    private Integer id;
    // 项目Id
    private Integer projectId;
    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;
    // 项目分数
    private double score;
    // 项目通过与否
    private boolean approval;
    // 项目不通过原因
    private String result;
    // 项目审核时间
    private LocalDate date;
    // 项目审核时提交的附件
    private String attachments;
    // 项目审核时，申请描述
    private String description;

}
