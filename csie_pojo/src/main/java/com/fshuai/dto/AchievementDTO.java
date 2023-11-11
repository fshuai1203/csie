package com.fshuai.dto;


import lombok.Data;

// 项目成果
@Data
public class AchievementDTO {

    // 论文
    private PaperDTO paperDTO;

    // 专利
    private PatentDTO patentDTO;

    // 实体作品
    private PhysicalProjectDTO physicalProjectDTO;

}

