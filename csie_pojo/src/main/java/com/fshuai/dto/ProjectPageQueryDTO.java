package com.fshuai.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectPageQueryDTO {

    private LocalDate beginTime;
    private Integer deptId;
    private String name;
    // 项目类型，包括校级或国家级等
    private Integer type;

    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;

    //  页码
    private int page;

    //  每页显示记录数
    private int pageSize;

}
