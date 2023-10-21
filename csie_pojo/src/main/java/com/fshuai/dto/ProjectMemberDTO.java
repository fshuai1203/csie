package com.fshuai.dto;


import lombok.Data;

import java.util.List;


@Data
public class ProjectMemberDTO {

    private Integer id;

    private List<MemberDTO> members;


}
