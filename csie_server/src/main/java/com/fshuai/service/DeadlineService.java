package com.fshuai.service;

import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.entity.ProjectDeadline;
import com.fshuai.vo.ProjectDeadlineVO;

public interface DeadlineService {
    ProjectDeadlineVO getDeadline();

    void updateDeadLine(ProjectDeadlineDTO projectDeadlineDTO);
}
