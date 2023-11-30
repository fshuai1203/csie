package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

  private Integer id;
  private String title;
  private String content;
  private String attachments;
  private LocalDate publishDate;
  private Integer state;
  private Integer publishUser;
  private Integer deptId;

}
