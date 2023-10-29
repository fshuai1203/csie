package com.fshuai.constant;

/**
 * 状态常量，启用或者禁用
 */
public class ProjectStateConstant {

    // 提出立项，等待立项审核通过
    public static final Integer APPROVAL_WAIT_APPLY = 1;

    // 立项审核失败
    public static final Integer APPROVAL_REVIEW_FAILED = 2;

    // 立项审核成功，等待中期审核
    public static final Integer APPROVAL_REVIEW_SUCCEED = 3;

    // 等待中期审核通过
    public static final Integer MIDTERM_WAIT_APPLY = 3;

    // 中期审核失败
    public static final Integer MIDTERM_REVIEW_FAILED = 4;

    // 中期审核成功，等待结项审核
    public static final Integer MIDTERM_REVIEW_SUCCEED = 5;

    // 等待结项审核通过
    public static final Integer COMPLETION_WAIT_APPLY = 5;

    // 结项审核失败
    public static final Integer COMPLETION_REVIEW_FAILED = 6;

    // 结项审核成功
    public static final Integer COMPLETION_REVIEW_SUCCEED = 7;

    // 项目延期审核申请
    public static final Integer POSTPONE_WAIT_APPLY = 8;

    // 延期审核失败
    public static final Integer POSTPONE_REVIEW_FAILED = 9;

    // 结项审核成功
    public static final Integer POSTPONE_REVIEW_SUCCEED = 10;

}
