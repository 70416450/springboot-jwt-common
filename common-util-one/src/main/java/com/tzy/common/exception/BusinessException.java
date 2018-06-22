package com.tzy.common.exception;

import java.text.MessageFormat;

/**
 * @Created by Heaton on 2018/5/3.
 * @describe 业务异常通常根据业务的需要
 */
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, Object... params) {
        super(MessageFormat.format(errorCode.getErrorMsg(), params));
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable e, Object... params) {
        super(MessageFormat.format(errorCode.getErrorMsg(), params), e);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
