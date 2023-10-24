package com.fshuai.mapper;

import com.fshuai.dto.TeacherDTO;
import com.fshuai.dto.TeacherPageQueryDTO;
import com.fshuai.entity.Teacher;
import com.fshuai.vo.TeacherVO;
import com.github.pagehelper.Page;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    Teacher selectByIdNumber(String idNumber);

    void insert(Teacher teacher);

    Teacher selectById(Integer teacherID);

    void updateByIdNumber(Teacher teacher);

    Page<TeacherVO> pageQuery(TeacherPageQueryDTO teacherPageQueryDTO);

    void deleteBatchByIds(List<Integer> ids);

    List<Teacher> selectByIdsCheckRoleOrNotDept(List<Integer> ids,Integer role,Integer deptId);
}
