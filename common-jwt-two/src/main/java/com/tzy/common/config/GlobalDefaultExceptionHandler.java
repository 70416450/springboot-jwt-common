package com.tzy.common.config;

import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author Heaton
 * @date 2018/6/10 23:08
 * @describe 全局异常处理, 注意如果有定义的intercepor, 其中afterCompletion中是获取不到任何异常的。
 * @ControllerAdvice中的异常处理在intercepor之前。
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalDefaultExceptionHandler {


    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonData handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error("parameter_validation_fail", e);
        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonData handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);

        return JsonData.error(JsonData.CODE_FAILURE, message);
    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(BindException.class)
    public JsonData handleBindException(BindException e) {
        log.error("parameter_bind_fail", e);

        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return JsonData.error(JsonData.CODE_FAILURE, message);

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public JsonData handleServiceException(ConstraintViolationException e) {
        log.error("parameter_validation_fail", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return JsonData.error(JsonData.CODE_FAILURE, message);
    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(ValidationException.class)
    public JsonData handleValidationException(ValidationException e) {
        log.error("parameter_validation_fail", e);

        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public JsonData handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("parameter_validation_fail", e);

        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(IllegalStateException.class)
    public JsonData handleIllegalStateException(IllegalStateException e) {
        log.error("parameter_validation_fail", e);

        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());

    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());

    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public JsonData handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("mediatype_not_support", e);
        return JsonData.error(JsonData.CODE_FAILURE, e.getMessage());
    }


    @ExceptionHandler({BusinessException.class})
    public JsonData businessException(BusinessException e) {
        int errorCode = 1;
        if (e.getErrorCode() != null) {
            errorCode = e.getErrorCode().getCode();
        }
        log.error("BusinessException，errorCode=" + errorCode, e);
        return JsonData.error(errorCode, e.getMessage());
    }

    @ExceptionHandler({SystemException.class})
    public JsonData systemException(SystemException e) {
        int errorCode = -1;
        if (e.getErrorCode() != null) {
            errorCode = e.getErrorCode().getCode();
        }
        log.error("SystemException，errorCode=" + errorCode, e);
        return JsonData.error(errorCode, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public JsonData allException(Exception e) {
        log.error("系统异常", e);
        return JsonData.<String>error(CommonErrorCode.INNER_ERROR.getCode(), "System error：" + e.getMessage());
    }


}
