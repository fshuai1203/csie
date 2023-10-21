package com.fshuai.handler;

import com.fshuai.constant.MessageConstant;
import com.fshuai.exception.BaseException;
import com.fshuai.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String msg = ex.getMessage();
        // sql异常是唯一键异常
        if (msg.contains("Duplicate entry")) {
            msg = ex.getMessage().split(" ")[2] + MessageConstant.ALREADY_EXIST;
            return Result.error(msg);
        } else {
            log.error("sql 错误：" + ex);
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
