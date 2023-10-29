package com.fshuai.mapper;

import com.fshuai.dto.MemberDTO;
import com.fshuai.dto.ProjectFinancePageDTO;
import com.fshuai.entity.ProjectFinance;
import com.fshuai.entity.ProjectMember;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectFinanceMapper {
    void insert(ProjectFinance projectFinance);

    Page<ProjectFinance> pageQuery(ProjectFinancePageDTO projectFinancePageDTO);

    ProjectFinance selectByProjectIdAndFinanceNumber(Integer projectId, String financeNumber);

    void update(ProjectFinance projectFinance);
}
