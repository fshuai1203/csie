package com.fshuai.controller.student;

import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/announcement")
@Api(tags = "学生公告相关接口")
@Slf4j
public class StudentAnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @GetMapping("/page")
    @ApiOperation("公告查询")
    public Result<PageResult> getAnnouncement(AnnouncementPageQueryDTO announcementDTO) {
        log.info("查询公告{}",announcementDTO);
        PageResult pageResult = announcementService.pageQuery(announcementDTO);
        return Result.success(pageResult);
    }

}
