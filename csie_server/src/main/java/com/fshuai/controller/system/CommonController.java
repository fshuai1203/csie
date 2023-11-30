package com.fshuai.controller.system;

import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.result.PageResult;
import com.fshuai.result.Result;
import com.fshuai.service.AnnouncementService;
import com.fshuai.service.DepartService;
import com.fshuai.vo.AnnouncementDetailVO;
import com.fshuai.vo.DepartmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common")
@Api(tags = "公共接口")
@Slf4j
public class CommonController {

    @Autowired
    DepartService departService;

    @GetMapping("/dept")
    @ApiOperation("查询系别")
    public Result<List<DepartmentVO>> getDepart() {
        return Result.success(departService.getAllDepart());
    }

}
