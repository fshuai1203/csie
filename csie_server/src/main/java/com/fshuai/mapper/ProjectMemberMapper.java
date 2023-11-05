package com.fshuai.mapper;

import com.fshuai.dto.MemberDTO;
import com.fshuai.entity.Project;
import com.fshuai.entity.ProjectMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMemberMapper {
    void insertByMembersAndProjectId(List<MemberDTO> members, Integer projectId);

    List<ProjectMember> selectByMemberId(Integer memberId);

    List<Integer> selectProjectIdsByMemberId(Integer studentID);

    @Delete("delete from project_member where project_id = #{projectId}")
    void deleteByProjectId(Integer projectId);

    List<Integer> selectMemberIdsByProjectId(Integer projectId);
}
