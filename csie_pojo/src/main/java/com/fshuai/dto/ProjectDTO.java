package com.fshuai.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    // 项目名称
    private String name;
    // 项目级别创新类或创业类
    private Integer category;
    // 指导教师
    private MemberDTO teacher;

    // 负责人id
    private MemberDTO principal;

    private List<MemberDTO> members;

    // 校外指导人
    private Integer mentorId;

    // 申报时间
    private LocalDate beginTime;

    // 结项时间
    private LocalDate endTime;
    // 项目描述
    private String description;
    // 项目附件，存放在项目自己的文件夹下，这字段通过json保存项目文件名
    private List<String> attachments;

    // 项目类型，包括校级或国家级等
    private Integer type;
    // 校级项目所属院系
    private Integer deptId;

}
