package com.fshuai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {

    private Integer deptId;
    private Integer majorId;
    private String password;
    private String phone;
    private String avatar;
}
