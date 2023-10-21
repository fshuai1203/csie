package com.fshuai.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLoginVO {
    private Integer id;
    private String name;
    private String idNumber;
    private Integer sex;
    private String departName;
    private String majorName;
    private String phone;
    private Integer role;
    // 返回jwt令牌
    private String token;
}
