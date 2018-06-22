package com.tzy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体Bean工具类
 */
@Slf4j
public class BeanUtil {

    public static final Example createExample(Class<?> type, Object... kv) {

        Example example = new Example(type);
        Criteria criteria = example.createCriteria();

        int size = kv.length % 2 == 0 ? kv.length / 2 : kv.length - 1;
        for (int i = 0; i <= size; i += 2) {
            criteria.andEqualTo(kv[i].toString(), kv[i + 1]);
        }

        return example;
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static <T> List<T> copyList(List<?> source, Class<T> target) {
        return source.stream().map(o -> {
            T instance = null;
            try {
                instance = target.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
            copyProperties(o, instance);
            return instance;
        }).collect(Collectors.toList());
    }


    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝不为空的值
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
