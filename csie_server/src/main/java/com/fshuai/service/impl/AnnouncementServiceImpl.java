package com.fshuai.service.impl;

import com.fshuai.dto.AnnouncementDTO;
import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.result.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementServiceImpl implements com.fshuai.service.AnnouncementService {

    @Override
    public PageResult pageQuery(AnnouncementPageQueryDTO announcementDTO) {
        return null;
    }

    @Override
    public void update(AnnouncementDTO announcementDTO) {

    }

    @Override
    public void add(AnnouncementDTO announcementDTO) {

    }

    @Override
    public void deleteBatch(List<Long> ids) {

    }
}
