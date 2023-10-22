package com.fshuai.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginVO {

    private Integer id;
    private String name;
    private String idNumber;
    private Integer sex;
    private Integer deptId;
    private Integer majorId;
    private String phone;
    // 返回jwt令牌
    private String token;
}
