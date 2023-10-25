package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectDeadlineDTO {

    private LocalDate approvalBeginDate;

    private LocalDate approvalEndDate;

    private LocalDate midtermBeginDate;

    private LocalDate midtermEndDate;

    private LocalDate completionBeginDate;

    private LocalDate completionEndDate;

}
