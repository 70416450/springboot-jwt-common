package com.tzy.common.exception;

public enum BizErrorCode implements ErrorCode {
    OBJECT_IS_NULL("对象为空。", 3000),
    ID_NULL("id为空。", 3001),
    DATA_INVALID("数据无效", 3002);

    private int code;
    private String errorMsg;

    BizErrorCode(String errorMsg, Integer code) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

}
