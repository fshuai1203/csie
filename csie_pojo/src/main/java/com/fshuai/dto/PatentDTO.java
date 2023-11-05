package com.fshuai.dto;

import lombok.Data;

// 专利
@Data
class PatentDTO {
    // 专利名称
    private String patentName;

    // 授权时间
    private String AuthorizationDate;

    // 专利号
    private String patentNumber;

}

