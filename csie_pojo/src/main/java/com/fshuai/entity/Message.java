package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  private Integer id;
  private String content;
  private Integer senderId;
  private Integer receiverId;
  private LocalDateTime sendDate;
  private Integer state;

}
