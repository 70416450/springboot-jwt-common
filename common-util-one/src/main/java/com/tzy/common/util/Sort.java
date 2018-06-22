package com.tzy.common.util;


import java.io.Serializable;

public final class Sort implements Serializable {

    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    private Sort() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Sort other = (Sort) obj;
        if (orderBy == null) {
            if (other.orderBy != null) return false;
        } else if (!orderBy.equals(other.orderBy)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sort [orderBy=" + orderBy + "]";
    }

    public static class SortBuilder {

        private SortBuilder() {
        }

        public static final Sort build(String sortBy, String sortType) {
            return build(new String[]{sortBy}, new String[]{sortType});
        }

        public static final Sort build(String[] sortBys, String[] sortTypes) {
            if (sortBys == null || sortBys.length == 0 || sortTypes == null || sortTypes.length != sortBys.length)
                return null;
            StringBuilder builder = new StringBuilder();
            for (int i = 0, l = sortBys.length; i < l; i++) {
                String sortBy = toUnderline(sortBys[i]);
                String sortType = sortTypes[i];
                if (sortBy == null || sortBy.trim().length() == 0) return null;
                if (sortType == null || !sortType.equalsIgnoreCase("desc")) sortType = "asc";
                if (i > 0) builder.append(",");
                builder.append(sortBy).append(" ").append(sortType);
            }
            Sort sort = new Sort();
            sort.orderBy = builder.toString();
            return sort;
        }

        /**
         * 驼峰转下划线
         *
         * @param str
         * @return
         */
        private static final String toUnderline(String str) {
            if (str == null) return null;
            StringBuilder builder = new StringBuilder();
            boolean upperCase = false;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                boolean nextUpperCase = true;
                if (i < str.length() - 1) nextUpperCase = Character.isUpperCase(str.charAt(i + 1));

                if (i >= 0 && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) if (i > 0) builder.append("_");
                    upperCase = true;
                } else upperCase = false;
                builder.append(Character.toLowerCase(c));
            }
            return builder.toString();
        }
    }
}
