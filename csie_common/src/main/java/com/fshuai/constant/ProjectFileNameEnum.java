package com.fshuai.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态常量，启用或者禁用
 */
@Getter
public enum ProjectFileNameEnum {

    APPROVAL_REVIEW(1, "立项审核材料"),

    MIDTERM_REVIEW(3, "中期审核材料"),
    MEMBERS_CHANGE(-1, "成员变更材料"),
    COMPLETION_REVIEW(6, "结项审核材料"),
    POSTPONE_WAIT(10, "延期审核材料");

    private Integer state;

    private String fileName;


    ProjectFileNameEnum(Integer state, String fileName) {
        this.state = state;
        this.fileName = fileName;
    }


}
