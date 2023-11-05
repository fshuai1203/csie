package com.fshuai.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProjectReviewPageQueryDTO {

    String states;
    //  页码
    private int page;

    //  每页显示记录数
    private int pageSize;
}
