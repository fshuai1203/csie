package com.fshuai.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class ProjectReviewDTO {

  // 项目Id
  private Integer projectId;
  // 项目状态，项目未通过state在原先state的基础上加1，项目通过在原先state的基础上加2
  private Integer state;
  // 项目分数
  private double score;
  // 项目通过与否，教师端必须指定
  private boolean approval;
  // 项目不通过原因
  private String result;

}
