package com.tzy.common.util;


import java.io.Serializable;

public final class Page implements Serializable {

    private int pageNum;
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    private Page() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pageNum;
        result = prime * result + pageSize;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Page other = (Page) obj;
        if (pageNum != other.pageNum) return false;
        if (pageSize != other.pageSize) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Page [pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
    }

    public static class PageBuilder {

        private PageBuilder() {
        }

        public static final Page build(String pageNum, String pageSize) {
            if (pageNum == null || !pageNum.matches("[1-9]\\d*")) return null;
            if (pageSize == null || !pageSize.matches("[1-9]\\d*")) pageSize = "10";
            return build(new Integer(pageNum), new Integer(pageSize));
        }

        public static final Page build(Integer pageNum, Integer pageSize) {
            if (pageNum == null || pageNum.intValue() == 0) return null;
            if (pageSize == null || pageSize.intValue() < 1) pageSize = 10;
            Page page = new Page();
            page.pageNum = pageNum.intValue();
            page.pageSize = pageSize.intValue();
            return page;
        }
    }


}
