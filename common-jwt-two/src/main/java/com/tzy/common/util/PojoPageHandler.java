package com.tzy.common.util;

import java.io.Serializable;

/**
 * @Created by Heaton on 2018/5/14.
 * @describe 分页执行者
 */
public class PojoPageHandler implements Serializable {

    /**
     * @param
     * @return
     * @date 2018/5/14 11:31
     * @describe 执行分页和排序
     */
    public static void doPagingAndSorting(Object obj) {
        try {
            Class<?> clazz = obj.getClass();
            String sort = (String) clazz.getMethod("getSort").invoke(obj);
            String order = (String) clazz.getMethod("getOrder").invoke(obj);
            Integer pageNum = (Integer) clazz.getMethod("getPageNum").invoke(obj);
            Integer pageSize = (Integer) clazz.getMethod("getPageSize").invoke(obj);
            Sort sortObj = Sort.SortBuilder.build(sort, order);
            Page pageObj = Page.PageBuilder.build(pageNum, pageSize);
            PageSortUtil.doPagingAndSorting(pageObj, sortObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


