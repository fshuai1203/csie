package com.fshuai.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReviewApplyDTO {

    // 项目Id
    private Integer projectId;
    // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
    private Integer state;
    // 项目审核时提交的附件
    private List<String> attachments;

}
