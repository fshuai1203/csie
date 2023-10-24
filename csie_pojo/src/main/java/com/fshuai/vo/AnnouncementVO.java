package com.fshuai.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class AnnouncementVO {

    private Integer id;
    private String title;
    private String content;
    private String attachments;
    private LocalDate publishDate;
    private Integer deptId;
    private String deptName;

}
