package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementPageQueryDTO {

    private String title;
    private LocalDate publishDate;
    private Integer deptId;
    private Integer category;
    //  页码
    private int page;

    //  每页显示记录数
    private int pageSize;

}
