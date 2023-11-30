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
public class ProjectVO {


    private Integer id;
    // 项目名称
    private String name;
    // 项目编号
    private String number;
    // 指导教师
    private String teacherName;

    // 负责人
    private String principalName;

    // 系名称
    private String deptName;

    private String projectMembersName;

    // 申报时间
    private LocalDate beginTime;

    // type类型
    private String typeStr;

    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;

    // 项目状态字符表示形式
    private String stateStr;


}
