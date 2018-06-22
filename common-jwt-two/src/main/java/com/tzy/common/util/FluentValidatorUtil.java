package com.tzy.common.util;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.exception.MyException;
import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Validation;
import java.util.List;
import java.util.Locale;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @Created by Heaton on 2018/5/10.
 * @describe FluentValidator解决集合验证
 */
@Slf4j
public class FluentValidatorUtil {


    private static ThreadLocal<javax.validation.Validator> VALIDATOR = new ThreadLocal<javax.validation.Validator>() {
        @Override
        protected javax.validation.Validator initialValue() {
            return Validation.buildDefaultValidatorFactory().getValidator();
        }
    };

    public static <T> void resultAdd(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new BusinessException(CommonErrorCode.OBJECT_IS_NULL);
        }
        Locale.setDefault(Locale.ENGLISH); // 设置语言
        Result ret = FluentValidator.checkAll(new Class<?>[]{AddGroup.class}).failFast()
                .onEach(list, new HibernateSupportedValidator<T>().setHiberanteValidator(VALIDATOR.get()))
                .doValidate().result(toSimple());
        if (!ret.isSuccess()) {
            log.error(ret.toString());
            try {
                throw new MyException(ret.toString());
            } catch (MyException e) {
                e.printStackTrace();
                throw new BusinessException(CommonErrorCode.ILLEGAL_LIST_ERROR);
            }

        }
    }

    public static <T> void resultUpdate(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new BusinessException(CommonErrorCode.OBJECT_IS_NULL);
        }
        Locale.setDefault(Locale.ENGLISH); // 设置语言
        Result ret = FluentValidator.checkAll(new Class<?>[]{UpdateGroup.class}).failFast()
                .onEach(list, new HibernateSupportedValidator<T>().setHiberanteValidator(VALIDATOR.get()))
                .doValidate().result(toSimple());
        if (!ret.isSuccess()) {
            log.error(ret.toString());
            try {
                throw new MyException(ret.toString());
            } catch (MyException e) {
                e.printStackTrace();
                throw new BusinessException(CommonErrorCode.ILLEGAL_LIST_ERROR);
            }

        }
    }


}
