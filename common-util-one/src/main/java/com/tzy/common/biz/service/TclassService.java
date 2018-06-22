package com.tzy.common.biz.service;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.model.Tclass;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Heaton
 * @date 2018/6/10 23:33
 */
public interface TclassService {

    List<Tclass> findAll(CommonSelectDto commonSelectDto);

    List<Tclass> findAllLikeJoinStudent(CommonSelectDto commonSelectDto);

    void addList(List<Tclass> tclasses);

    void updateList(List<Tclass> tclass);

    void delete(Long[] ids);

    //导出Excel
    void export(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response);

    //导出Excel   和班级相关的学生也导出
    void exportJoinChild(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response);

    //按模板导出Excel   和班级相关的学生也导出
    void exportExcleTemplate(String queryCriteria, Long[] ids, HttpServletResponse response);
}