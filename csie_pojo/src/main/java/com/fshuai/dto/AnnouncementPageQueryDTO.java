package com.fshuai.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AnnouncementPageQueryDTO {

    private String title;

    private String publishDate;
    private Integer deptId;
    //  页码
    private int page;

    //  每页显示记录数
    private int pageSize;

}
