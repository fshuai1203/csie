package com.fshuai.dto;

import lombok.Data;

@Data
public class TeacherRegisterDTO {

    private String name;
    private String idNumber;
    private Integer sex;
    private Integer deptId;
    private String majorName;
    private String phone;
    private Integer role;

}
