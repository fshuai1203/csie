package com.fshuai.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  private Integer id;
  private String name;
  private String idNumber;
  private Integer sex;
  private Integer deptId;
  private String majorName ;
  private String password;
  private String phone;
  private Integer state;

}
