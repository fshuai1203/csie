package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private Integer id;
    // 项目名称
    private String name;
    // 项目编号
    private String number;
    // 项目级别创新类或创业类
    private Integer category;
    // 指导教师
    private Integer teacherId;
    // 指导教师
    private String teacherName;

    // 负责人id
    private Integer principalId;
    // 负责人
    private String principalName;

    // 校外指导人
    private Integer mentorId;

    // 申报时间
    private LocalDate beginTime;

    // 结项时间
    private LocalDate endTime;
    // 项目描述
    private String description;
    // 项目附件，存放在项目自己的文件夹下，这字段通过json保存项目文件名
    private String attachments;
    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;
    // 项目成果，包括论文，专利，实物，通过json字段存储
    private String achievement;
    // 项目类型，包括校级或国家级等
    private Integer type;
    // 校级项目所属院系
    private Integer deptId;

}
