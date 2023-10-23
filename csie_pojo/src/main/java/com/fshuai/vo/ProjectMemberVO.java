package com.fshuai.vo;


import lombok.Data;


@Data
public class ProjectMemberVO {

    private Integer id;
    private String name;
    private String idNumber;

    private Integer deptId;
    private String majorName ;
    private String phone;

}
