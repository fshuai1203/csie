package com.fshuai.controller.teacher;

import com.fshuai.dto.AnnouncementDTO;
import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher/announcement")
@Api(tags = "公告相关接口")
@Slf4j
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @GetMapping("/page")
    @ApiOperation("公告查询")
    public Result<PageResult> getAnnouncement(AnnouncementPageQueryDTO announcementDTO) {
        PageResult pageResult = announcementService.pageQuery(announcementDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("修改公告")
    public Result updateAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        announcementService.update(announcementDTO);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("添加公告")
    public Result addAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        announcementService.add(announcementDTO);
        return Result.success();
    }

    @ApiOperation("批量删除公告")
    @DeleteMapping()
    public Result<PageResult> deleteByIds(@RequestParam List<Long> ids) {
        announcementService.deleteBatch(ids);
        return Result.success();
    }

}
