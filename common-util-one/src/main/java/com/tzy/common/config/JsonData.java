package com.tzy.common.config;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.exception.ErrorCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class JsonData<T> {

    private final static Integer CODE_SUCCESS = 200;
    private final static String msgSuccess = "success";

    public final static Integer CODE_FAILURE = -1;

    private Integer code;
    private String msg;
    private T data;
    private Long timestamp;


    public JsonData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        timestamp = System.currentTimeMillis();
    }

    public JsonData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        timestamp = System.currentTimeMillis();
    }

    public static <T> JsonData<T> ok(T data) {
        return new JsonData<>(CODE_SUCCESS, msgSuccess, data);
    }

    public static <T> JsonData ok(List<T> data) {
        if (data instanceof Page) {
            Map<String, Object> map = new HashMap();

            List<T> list = new ArrayList<T>();
            for (T object : data) {
                list.add(object);
            }
            map.put("list", list);
            PageInfo info = new PageInfo(data);
            map.put("total", info.getTotal());
            map.put("pages", info.getPages());
            map.put("size", info.getSize());
            map.put("pageSize", info.getPageSize());
            map.put("pageNum", info.getPageNum());
            return new JsonData(CODE_SUCCESS, msgSuccess, map);
        } else {
            return new JsonData(CODE_SUCCESS, msgSuccess, data);
        }
    }

    public static <T> JsonData ok(List<T> data, boolean isPage) {
        if (isPage) {
            //TODO
        }
        return new JsonData<>(CODE_SUCCESS, msgSuccess, data);
    }

    public static JsonData ok() {
        return new JsonData(CODE_SUCCESS, msgSuccess);
    }

    public static JsonData error(ErrorCode errorCode) {
        return new JsonData(errorCode.getCode(), errorCode.getErrorMsg());
    }

    public static JsonData error() {
        return error(CommonErrorCode.INNER_ERROR);
    }

    public static JsonData error(Integer errorCode, String msg) {
        return new JsonData(errorCode, msg);
    }
}
