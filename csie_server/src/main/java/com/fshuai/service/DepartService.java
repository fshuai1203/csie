package com.fshuai.service;

import com.fshuai.dto.AnnouncementDTO;
import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.dto.AnnouncementUpdateDTO;
import com.fshuai.result.PageResult;
import com.fshuai.vo.AnnouncementDetailVO;
import com.fshuai.vo.DepartmentVO;

import java.util.List;

public interface DepartService {
    List<DepartmentVO> getAllDepart();

    String getDepartNameById(Integer id);

}
