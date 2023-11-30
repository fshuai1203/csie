package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mentor {

  private Integer id;
  private String name;
  private String idNumber;
  private Integer sex;
  private String password;
  private String phone;
  private String avatar;
  private Integer role;
  private Integer state;

}
