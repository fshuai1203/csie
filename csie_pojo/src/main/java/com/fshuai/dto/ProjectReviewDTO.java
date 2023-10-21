package com.fshuai.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class ProjectReviewDTO {

  private Integer id;
  // 项目Id
  private Integer projectId;
  // 项目状态，包括待立项审核，待中期审核，待结项审核，待延期审核
  private Integer state;
  // 项目分数
  private double score;
  // 项目通过与否
  private boolean approval;
  // 项目不通过原因
  private String result;

}
