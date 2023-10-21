package com.fshuai.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnnouncementDTO {

    private String title;

    private String content;

    private List<String> attachments;

}
