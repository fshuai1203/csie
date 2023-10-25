package com.fshuai.exception;

/**
 * 账号不存在异常
 */
public class DeadlineException extends BaseException {

    public DeadlineException() {
    }

    public DeadlineException(String msg) {
        super(msg);
    }

}
