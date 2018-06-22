package com.tzy.common.exception;

/**
 * @author Heaton
 * @date 2018/5/11 9:40
 * @describe 用于直接打印验证异常msg
 */
public class MyException extends Exception {
    public MyException() {
    }

    public MyException(String msg) {
        super(msg);
    }

}