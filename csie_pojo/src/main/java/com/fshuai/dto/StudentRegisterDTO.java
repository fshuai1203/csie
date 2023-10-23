package com.fshuai.dto;

import lombok.Data;

@Data
public class StudentRegisterDTO {

    private String name;
    private String idNumber;
    private Integer sex;
    private Integer deptId;
    private String majorName ;
    private String password;
    private String phone;
    private String avatar;
}
