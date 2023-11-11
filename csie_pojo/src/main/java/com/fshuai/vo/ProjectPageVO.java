package com.fshuai.vo;


import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPageVO {

    private Integer id;
    // 项目名称
    private String name;
    // 项目编号
    private String number;

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

    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;

    // 项目类型，包括校级或国家级等
    private Integer type;

    // 校级项目所属院系
    private Integer deptId;
    // 校级项目所属院系
    private String deptName;


}
