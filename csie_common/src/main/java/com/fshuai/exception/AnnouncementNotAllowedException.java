package com.fshuai.exception;

/**
 * 账号不存在异常
 */
public class AnnouncementNotAllowedException extends BaseException {

    public AnnouncementNotAllowedException() {
    }

    public AnnouncementNotAllowedException(String msg) {
        super(msg);
    }

}
