package com.fshuai.dto;


import lombok.Data;

import java.util.List;


@Data
public class ProjectMemberDTO {

    private Integer projectId;

    private List<MemberDTO> members;


}
