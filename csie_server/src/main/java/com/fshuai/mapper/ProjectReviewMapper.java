package com.fshuai.mapper;

import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.dto.AnnouncementUpdateDTO;
import com.fshuai.dto.ProjectReviewApplyDTO;
import com.fshuai.entity.Announcement;
import com.fshuai.entity.ProjectReview;
import com.fshuai.vo.AnnouncementVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectReviewMapper {

    void insert(ProjectReviewApplyDTO reviewApplyDTO);

    ProjectReview selectByProjectIdAndState(Integer projectId, Integer state);

    void updateProjectReview(ProjectReview projectReview);
}
