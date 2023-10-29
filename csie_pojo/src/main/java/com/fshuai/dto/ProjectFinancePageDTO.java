package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectFinancePageDTO {

    // 申请报销时间
    private String applyTime;
    // 项目类型，包括校级或国家级等
    private Integer type;
    // 校级项目所属院系
    private Integer deptId;
    // 项目名称
    private String projectName;
    // 报销状态
    private Integer state;
    //  页码
    private int page;
    //  每页显示记录数
    private int pageSize;

}
