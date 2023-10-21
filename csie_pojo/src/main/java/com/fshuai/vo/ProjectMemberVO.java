package com.fshuai.vo;


import lombok.Data;


@Data
public class ProjectMemberVO {

    private Integer id;
    private String name;
    private String idNumber;

    private Integer deptId;
    private Integer majorId;
    private String phone;

}
