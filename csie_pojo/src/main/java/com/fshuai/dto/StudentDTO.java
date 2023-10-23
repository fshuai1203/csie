package com.fshuai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Integer deptId;
    private String majorName ;
    private String password;
    private String phone;
    private String avatar;
}
