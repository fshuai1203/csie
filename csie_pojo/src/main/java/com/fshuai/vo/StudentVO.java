package com.fshuai.vo;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StudentVO {

    private Integer id;
    private String name;
    private String idNumber;

    private Integer deptId;
    private String majorName ;
    private String phone;

}
