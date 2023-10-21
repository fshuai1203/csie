package com.fshuai.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFinanceVO {

    private Integer id;
    // 项目报销单号
    private String financeNumber;
    // 项目ID
    private Integer projectId;
    // 项目名称
    private String projectName;
    // 项目类型，包括校级或国家级等
    private Integer type;
    // 校级项目所属院系
    private Integer deptId;
    // 负责人名字
    private String principalName;
    // 项目报销金额
    private double price;
    // 申请报销时间
    private LocalDate reimbursementTime;
    // 操作人
    private Integer processor;
    // 附件
    private String attachments;
}
