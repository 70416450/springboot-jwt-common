package com.tzy.common.util;

import com.github.pagehelper.PageHelper;

public class PageSortUtil {
    /**
     * 执行分页和排序
     *
     * @param page
     * @param sort
     */
    public static void doPagingAndSorting(Page page, Sort sort) {
        if (page != null) PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (sort != null) PageHelper.orderBy(sort.getOrderBy());
    }

    /**
     * 执行分页
     *
     * @param page
     */
    public static void doPaging(Page page) {
        doPagingAndSorting(page, null);
    }

    /**
     * 执行分页
     *
     * @param pageNum
     * @param pageSize
     */
    public static void doPaging(Integer pageNum, Integer pageSize) {
        Page page = Page.PageBuilder.build(pageNum, pageSize);
        doPaging(page);
    }

    /**
     * 执行分页
     *
     * @param pageNum
     */
    public static void doPaging(Integer pageNum) {
        doPaging(pageNum, null);
    }

    /**
     * 执行排序
     *
     * @param sort
     */
    public static void doSorting(Sort sort) {
        doPagingAndSorting(null, sort);
    }

    /**
     * 执行排序
     *
     * @param sortBys
     * @param sortTypes
     */
    public static void doSorting(String[] sortBys, String[] sortTypes) {
        Sort sort = Sort.SortBuilder.build(sortBys, sortTypes);
        doSorting(sort);
    }

    /**
     * 执行排序
     *
     * @param sortBy
     * @param sortType
     */
    public static void doSorting(String sortBy, String sortType) {
        doSorting(new String[]{sortBy}, new String[]{sortType});
    }

    /**
     * 执行排序
     *
     * @param sortBy
     */
    public static void doSorting(String sortBy) {
        doSorting(sortBy, null);
    }
}
