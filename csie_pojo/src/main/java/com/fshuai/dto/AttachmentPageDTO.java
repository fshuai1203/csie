package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AttachmentPageDTO {

    private LocalDate beginTime;

    private LocalDate endTime;

    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;

    private String projectIds;

}
