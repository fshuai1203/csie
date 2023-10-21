package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class ProjectFinanceDTO {

    // 项目ID
    private Integer projectId;
    // 项目报销金额
    private double price;
    // 附件
    private List<String> attachments;

}
