package com.fshuai.vo;


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
public class ProjectDetailVO {


    private Integer id;
    // 项目名称
    private String name;
    // 项目编号
    private String number;
    // 项目级别创新类或创业类
    private Integer category;
    // 指导教师
    private String teacherName;

    // 负责人信息
    private ProjectMemberVO principal;

    private List<ProjectMemberVO> projectMembers;

    // 校外指导人
    private Integer mentorId;

    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;


}
