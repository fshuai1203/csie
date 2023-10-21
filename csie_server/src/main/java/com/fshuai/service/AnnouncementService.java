package com.fshuai.service;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;
import com.fshuai.vo.AnnouncementDetailVO;

import java.util.List;

public interface AnnouncementService {
    PageResult pageQuery(AnnouncementPageQueryDTO announcementDTO);

    void update(AnnouncementUpdateDTO announcementUpdateDTO);

    void add(AnnouncementDTO announcementDTO);

    void deleteBatch(List<Integer> ids);

    AnnouncementDetailVO detail(Integer id);
}
