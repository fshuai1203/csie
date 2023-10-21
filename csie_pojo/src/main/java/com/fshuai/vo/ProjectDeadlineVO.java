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
public class ProjectDeadlineVO {

    private LocalDate approvalBeginDate;
    private LocalDate approvalEndDate;
    private LocalDate midtermBeginDate;
    private LocalDate midtermEndDate;
    private LocalDate completionBeginData;
    private LocalDate completionEndData;
    private LocalDate postponeBeginData;
    private LocalDate postponeEndData;

}
