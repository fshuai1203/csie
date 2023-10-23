package com.fshuai.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {
    private Integer id;
    private String name;
    private String idNumber;
    private Integer deptId;
    private String deptName;
    private String majorName;
    private Integer role;
}
