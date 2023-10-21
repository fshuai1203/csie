package com.fshuai.utils;

import com.fshuai.constant.ProjectStateConstant;
import com.fshuai.constant.TypeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StringUtil {

    private static Map<Integer, String> typeMap;
    private static Map<Integer, String> stateMap;

    public String listTOString(List integers) {
        if (integers == null || integers.isEmpty() || integers.get(0) == null) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(integers.get(0));
        for (int i = 1; i < integers.size(); i++) {
            buffer.append(",");
            buffer.append(integers.get(i));
        }
        return buffer.toString();
    }

    public String getTypeStr(Integer type) {
        if (typeMap == null) {
            typeMap = new HashMap<>();
            typeMap.put(TypeConstant.DEPT_TYPE, "系级");
            typeMap.put(TypeConstant.SCHOOL_TYPE, "校级");
            typeMap.put(TypeConstant.COUNTRY_TYPE, "国家级");
        }
        return typeMap.get(type);
    }

    public String getStateStr(Integer state) {
        if (stateMap == null) {
            stateMap = new HashMap<>();
            stateMap.put(ProjectStateConstant.APPROVAL_WAIT_APPLY, "项目申报：待审核");
            stateMap.put(ProjectStateConstant.APPROVAL_REVIEW_FAILED, "项目申报：审核失败");
            stateMap.put(ProjectStateConstant.APPROVAL_REVIEW_SUCCEED, "项目申报：审核成功");
            stateMap.put(ProjectStateConstant.MIDTERM_WAIT_APPLY, "项目中期：待审核");
            stateMap.put(ProjectStateConstant.MIDTERM_REVIEW_FAILED, "项目中期：审核失败");
            stateMap.put(ProjectStateConstant.MIDTERM_REVIEW_SUCCEED, "项目中期：审核成功");
            stateMap.put(ProjectStateConstant.COMPLETION_WAIT_APPLY, "项目结项：待审核");
            stateMap.put(ProjectStateConstant.COMPLETION_REVIEW_FAILED, "项目结项：审核失败");
            stateMap.put(ProjectStateConstant.COMPLETION_REVIEW_SUCCEED, "项目结项：审核成功");
            stateMap.put(ProjectStateConstant.POSTPONE_WAIT_APPLY, "项目延期：待审核");
            stateMap.put(ProjectStateConstant.POSTPONE_REVIEW_FAILED, "项目延期：审核失败");
            stateMap.put(ProjectStateConstant.POSTPONE_REVIEW_SUCCEED, "项目延期：审核成功");
        }
        return stateMap.get(state);
    }

}
