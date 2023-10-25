package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectDeadlineUpdateDTO {

    private LocalDate approvalBeginDate;
    private LocalDate approvalEndDate;
    private LocalDate midtermBeginDate;
    private LocalDate midtermEndDate;
    private LocalDate completionBeginDate;
    private LocalDate completionEndDate;
    private LocalDate postponeBeginDate;
    private LocalDate postponeEndDate;

}
