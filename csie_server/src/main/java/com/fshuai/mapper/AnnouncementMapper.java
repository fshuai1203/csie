package com.fshuai.mapper;

import com.fshuai.dto.AnnouncementPageQueryDTO;
import com.fshuai.dto.AnnouncementUpdateDTO;
import com.fshuai.entity.Announcement;
import com.fshuai.vo.AnnouncementVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    void insert(Announcement announcement);

    Page<AnnouncementVO> pageQuery(AnnouncementPageQueryDTO announcementPageQueryDTO);

    // 根据deptId和ids查询公告（可以用来检查ids里面是否有越级的公告）
    List<Announcement> selectByDeptAndIds(Integer deptId, List<Integer> ids);

    void deleteBatchByIds(List<Integer> ids);

    void updateById(AnnouncementUpdateDTO announcementUpdateDTO);

}
