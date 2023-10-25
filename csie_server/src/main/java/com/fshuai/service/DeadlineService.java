package com.fshuai.service;

import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.dto.ProjectDeadlineUpdateDTO;
import com.fshuai.vo.ProjectDeadlineVO;

public interface DeadlineService {
    ProjectDeadlineVO getDeadline();

    void updateDeadLine(ProjectDeadlineUpdateDTO projectDeadlineUpdateDTO);

    void addDeadLine(ProjectDeadlineDTO projectDeadlineDTO);
}
