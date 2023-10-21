package com.fshuai.service.impl;

import com.fshuai.dto.ProjectDeadlineDTO;
import com.fshuai.service.DeadlineService;
import com.fshuai.vo.ProjectDeadlineVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeadlineServiceImpl implements DeadlineService {
    @Override
    public ProjectDeadlineVO getDeadline() {
        return null;
    }

    @Override
    public void updateDeadLine(ProjectDeadlineDTO projectDeadlineDTO) {

    }
}
