package com.fshuai.dto;

import lombok.Data;

@Data
public class TeacherPageQueryDTO {

    //  教师姓名
    private String name;

    // 教职工号
    private String idNumber;

    // 系别
    private Integer deptId;

    //  页码
    private int page;

    //  每页显示记录数
    private int pageSize;

}
