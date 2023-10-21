package com.fshuai.controller.teacher;

import com.fshuai.dto.AnnouncementDTO;
import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.dto.AnnouncementUpdateDTO;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.AnnouncementService;
import com.fshuai.vo.AnnouncementDetailVO;
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
        log.info("查询公告{}",announcementDTO);
        PageResult pageResult = announcementService.pageQuery(announcementDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("修改公告")
    public Result updateAnnouncement(@RequestBody AnnouncementUpdateDTO announcementUpdateDTO) {
        announcementService.update(announcementUpdateDTO);
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
    public Result<PageResult> deleteByIds(@RequestParam List<Integer> ids) {
        announcementService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("查询公告详情")
    public Result<AnnouncementDetailVO> getAnnouncementDetail(@PathVariable("id") Integer id) {
        AnnouncementDetailVO announcementDetail = announcementService.detail(id);
        return Result.success(announcementDetail);
    }

}
