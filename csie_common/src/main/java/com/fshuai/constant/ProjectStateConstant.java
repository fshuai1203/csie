package com.fshuai.constant;

/**
 * 状态常量，启用或者禁用
 */
public class ProjectStateConstant {

    // 提出立项，等待立项审核通过
    public static final Integer APPROVAL_WAIT_APPLY = 1;

    // 立项审核失败
    public static final Integer APPROVAL_REVIEW_FAILED = 2;

    // 立项审核成功，等待提交中期审核材料
    public static final Integer APPROVAL_REVIEW_SUCCEED = 3;

    // 材料已经提交，等待中期审核通过
    public static final Integer MIDTERM_WAIT_APPLY = 4;

    // 中期审核失败
    public static final Integer MIDTERM_REVIEW_FAILED = 5;

    // 中期审核成功，等待提交结项审核材料
    public static final Integer MIDTERM_REVIEW_SUCCEED = 6;

    // 结项审核材料提交成功，等待结项审核通过
    public static final Integer COMPLETION_WAIT_APPLY = 7;

    // 结项审核失败
    public static final Integer COMPLETION_REVIEW_FAILED = 8;

    // 结项审核成功
    public static final Integer COMPLETION_REVIEW_SUCCEED = 9;

    // 项目延期审核申请
    public static final Integer POSTPONE_WAIT_APPLY = 10;

    // 延期审核失败
    public static final Integer POSTPONE_REVIEW_FAILED = 11;

    // 延期审核成功
    public static final Integer POSTPONE_REVIEW_SUCCEED = 12;

}
