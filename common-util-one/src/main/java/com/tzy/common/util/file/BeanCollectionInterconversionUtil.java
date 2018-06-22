package com.tzy.common.util.file;

import com.github.pagehelper.Page;
import com.tzy.common.biz.model.Student;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Heaton
 * @date 2018/5/25 20:48
 * @describe BeanMAP互相转换
 */
public class BeanCollectionInterconversionUtil {

    public static void main(String[] args) {

        Student person = new Student();
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("userName", "Mike");
        mp.put("idCardNumber", "dasdasdasd");

        // 将map转换为bean
        transMap2Bean(mp, person.getClass());

        System.out.println("--- transMap2Bean Map Info: ");
        for (Map.Entry<String, Object> entry : mp.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("--- Bean Info: ");
        System.out.println("name: " + person.getUserName());
        System.out.println("age: " + person.getIdCard());

        // 将javaBean 转换为map
        Map<String, Object> map = transBean2Map(person);

        System.out.println("--- transBean2Map Map Info: ");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static final <BEAN> BEAN transMap2Bean(Map<String, Object> map, Class<BEAN> beanClass) {
        BEAN bean = null;
        if (map != null && beanClass == null) {
            try {
                bean = beanClass.newInstance();
                BeanUtils.populate(bean, map);
            } catch (Exception e) {
                throw new BusinessException(CommonErrorCode.BEAN_TRANS_ERROR);
            }
        }
        return bean;
    }


    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            //返回bean的所有属性的描述符
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.BEAN_TRANS_ERROR);
        }
        return;
    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

    /**
     * @param [beans]
     * @return java.util.List<java.util.Map       <       java.lang.String       ,       java.lang.Object>>
     * @describe 将List&lt;Bean&gt;转换为List&lt;Map&gt;
     */
    public static final <BEAN> List<Map<String, Object>> bean2map(List<BEAN> beans) {
        List<Map<String, Object>> dtos = null;
        if (beans instanceof Page) {
            Page<Map<String, Object>> sPage = (Page<Map<String, Object>>) beans;

            dtos = new Page<>();
            Page<Map<String, Object>> dPage = (Page<Map<String, Object>>) dtos;
            dPage.setTotal(sPage.getTotal());
            dPage.setPages(sPage.getPages());
            dPage.setPageNum(sPage.getPageNum());
            dPage.setPageSize(sPage.getPageSize());
        } else {
            dtos = new ArrayList<>(beans.size());
        }
        for (BEAN bean : beans)
            dtos.add(transBean2Map(bean));
        return dtos;
    }

    /**
     * @param [bean, dtoClass]
     * @return DTO
     * @describe 将Bean转换为DTO
     */
    public static final <BEAN, DTO> DTO bean2dto(BEAN bean, Class<DTO> dtoClass) {
        return transMap2Bean(transBean2Map(bean), dtoClass);
    }

    /**
     * @param [beans, dtoClass]
     * @return java.util.List<DTO>
     * @describe 将List&lt;Bean&gt;转换为List&lt;DTO&gt;
     */
    public static final <BEAN, DTO> List<DTO> bean2dto(List<BEAN> beans, Class<DTO> dtoClass) {
        List<DTO> dtos = null;
        if (beans instanceof Page) {
            Page<DTO> sPage = (Page<DTO>) beans;

            dtos = new Page<>();
            Page<DTO> dPage = (Page<DTO>) dtos;
            dPage.setTotal(sPage.getTotal());
            dPage.setPages(sPage.getPages());
            dPage.setPageNum(sPage.getPageNum());
            dPage.setPageSize(sPage.getPageSize());
        } else {
            dtos = new ArrayList<>(beans.size());
        }
        for (BEAN bean : beans)
            dtos.add(bean2dto(bean, dtoClass));
        return dtos;
    }

    /**
     * @param
     * @return
     * @describe Map转Bean异常
     */
    @SuppressWarnings("serial")
    private static class ConvertMapToBeanFailureException extends RuntimeException {

        public ConvertMapToBeanFailureException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * @param
     * @return
     * @describe Bean转Map异常
     */
    @SuppressWarnings("serial")
    private static class ConvertBeanToMapFailureException extends RuntimeException {

        public ConvertBeanToMapFailureException(Throwable cause) {
            super(cause);
        }
    }
}