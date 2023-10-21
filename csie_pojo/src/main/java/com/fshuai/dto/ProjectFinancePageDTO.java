package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectFinancePageDTO {

    // 申请报销时间
    private LocalDate reimbursementTime;
    // 项目类型，包括校级或国家级等
    private Integer type;
    // 校级项目所属院系
    private Integer deptId;
    // 项目名称
    private String projectName;
}
