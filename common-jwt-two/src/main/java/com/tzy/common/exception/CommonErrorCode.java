package com.tzy.common.exception;

/**
 * 核心模块错误(0-999),具体细分子模块：<br>
 * 0-99为系统常见异常的错误码，如IllegalArgumentException<br>
 * 100-199为日期相关错误<br>
 *
 * <br>
 * 900-999为action,state,audit相关的错误定义
 * <br>
 */
public enum CommonErrorCode implements ErrorCode {

    SYSTEM_ERROR(1, "系统异常:{0}"),
    SYSTEM_INVALID_CONFIG(2, "配置错误:{0}"),
    SYSTEM_CLASS_NOT_FOUND(3, "无法加载class:{0}"),
    SYSTEM_UNSUPPORTED_ENCODING(4, "不支持的编码:{0}"),
    SYSTEM_UNSUPPORTED_OPERATION(5, "不支持操作方式:{0}"),
    SYSTEM_USER_UNLOGED(9, "用户没有登录"),
    OBJECT_ALREADY_EXISTS(10, "对象已经存在:{0}"),
    OBJECT_IS_NULL(11, "对象为空:{0}"),
    OBJECT_NOT_EXISTS(12, "对象不存在:{0}"),
    BEAN_TRANS_ERROR(13, "Bean转换异常"),

    ILLEGAL_PARAM(20, "非法的参数{0}"),
    ILLEGAL_USER(21, "非法的用户{0}"),
    ILLEGAL_STATE(22, "非法的状态{0}"),
    ILLEGAL_PARAM_EMPTY(23, "参数{0}不能为空"),
    ILLEGAL_PARAM_NOT_INTEGER(24, "参数{0}必须为整数"),
    ILLEGAL_PARAM_NOT_POSITIVE_INTEGER(25, "参数{0}必须为正整数"),
    NO_EMPLOYEE(26, "身份证{0}不存在"),
    EMAIL_AUTHENTICATION(30, "邮件服务器认证不通过"),
    EMAIL_TEMPLATE(31, "邮件模板异常"),
    EMAIL_MESSAGE_ERROR(32, "邮件消息错误"),
    EMAIL_SEND_TO_NULL(33, "邮件发往的邮箱地址不能为空"),
    EMAIL_CONTENT_NULL(34, "邮件内容不能为空"),
    ILLEGAL_LIST_ERROR(35, "集合数据异常"),

    FILE_NO_EXIST(40, "文件{0}不存在"),
    FILE_READ_ERROR(41, "文件读取错误:{0}"),
    FILE_NO_SUPPORT(42, "文件{0}不支持"),
    FILE_UPLOAD_ERROR(43, "文件上传异常"),
    FILE_UPLOAD_NULL(44, "文件为空"),
    EXCEL_IS_NULL(45, "excel文件为空。"),
    TEMPLATE_IS_NULL(46, "模版为空。"),
    EXCEPTION_COMMON(47, "导入导出异常。"),

    //日期错误代码定义
    DATE_ILLEGAL_FORMAT(100, "日期{0}的格式非法"),
    DATE_FORMAT_ERROR(101, "字符串{0}无法转换成格式为{1}的日期"),
    DATE_START_NULL(102, "开始时间不能为空"),
    DATE_END_NULL(103, "结束时间不能为空"),
    DATE_END_LESSTHAN_NOW(104, "结束时间{0}不能小于当前时间"),
    DATE_END_BEFORE_START(105, "开始时间{0}必须早于结束时间{1}"),
    DATE_RANGE_OVERSTEP_DAYS(106, "开始时间{0}和结束时间{1}范围不能超过{2}天"),
    DATE_RANGE_LESSTHAN_DAYS(107, "开始时间{0}和结束时间{1}范围不能小于{2}天"),
    DATE_TYPE_ERROR(108, "时间与选择类型不符"),

    NO_SESSION(401, "未授权的访问！"),
    INNER_ERROR(500, "系统内部错误:{0}"),
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
    TOKEN_INVALID(404, "token无效");


    private int code;
    private String errorMsg;

    CommonErrorCode(int code, String errorMsg) {
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
