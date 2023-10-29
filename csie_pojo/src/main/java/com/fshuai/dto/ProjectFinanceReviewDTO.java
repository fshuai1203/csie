package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectFinanceReviewDTO {

    private Integer projectId;
    // 项目报销单号
    private String financeNumber;
    // 审核状态
    private Integer state;
    // 审核描述
    private String description;

}
