package com.fshuai.service;

import com.fshuai.dto.*;
import com.fshuai.result.PageResult;

import java.util.List;

public interface AnnouncementService {
    PageResult pageQuery(AnnouncementPageQueryDTO announcementDTO);

    void update(AnnouncementDTO announcementDTO);

    void add(AnnouncementDTO announcementDTO);

    void deleteBatch(List<Long> ids);
}
