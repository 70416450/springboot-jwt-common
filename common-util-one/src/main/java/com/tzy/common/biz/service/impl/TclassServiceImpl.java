package com.tzy.common.biz.service.impl;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.mapper.TclassMapper;
import com.tzy.common.biz.model.Tclass;
import com.tzy.common.biz.service.TclassService;
import com.tzy.common.biz.vo.JoinVo.TclassJoinVo;
import com.tzy.common.exception.BizErrorCode;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.util.DateUtil;
import com.tzy.common.util.PojoPageHandler;
import com.tzy.common.util.Validator;
import com.tzy.common.util.file.BeanCollectionInterconversionUtil;
import com.tzy.common.util.file.FileExcleUtil;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Heaton
 * @date 2018/6/10 23:39
 */
@Service
public class TclassServiceImpl implements TclassService {

    @Autowired
    private TclassMapper tclassMapper;

    @Value("${message.data.file.path}")
    private String oldFilePath;

    @Override
    public List<Tclass> findAll(CommonSelectDto commonSelectDto) {
        PojoPageHandler.doPagingAndSorting(commonSelectDto);
        List<Tclass> allLike = tclassMapper.findAllLike(commonSelectDto);
        return allLike;
    }

    @Override
    public List<Tclass> findAllLikeJoinStudent(CommonSelectDto commonSelectDto) {
        PojoPageHandler.doPagingAndSorting(commonSelectDto);
        List<Tclass> allLike = tclassMapper.findAllLikeJoinStudent(commonSelectDto);
        return allLike;
    }

    @Override
    public void addList(List<Tclass> list) {
        for (Tclass tclass : list) {
            tclass.setId(IdUtil.generateId());
            tclass.setIsDel(false);
        }
        tclassMapper.addList(list);
    }

    @Override
    public void updateList(List<Tclass> list) {
        tclassMapper.updateList(list);
    }


    @Override
    public void delete(Long[] ids) {
        Example example = getExample(ids);
        Tclass tclass = Tclass.builder().isDel(true).build();
        tclassMapper.updateByExampleSelective(tclass, example);

    }

    //查询符合条件的
    private Example getExample(Long[] idss) {
        if (!Validator.valid(idss)) {
            throw new BusinessException(BizErrorCode.ID_NULL);
        }
        List<Long> ids = Arrays.asList(idss);
        Example example = new Example(Tclass.class);
        example.createCriteria().andIn("id", ids);
        return example;
    }

    @Override
    public void export(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        if (!isExportAll) {
            if (Validator.valid(queryCriteria)) {
                map.put("queryCriteria", queryCriteria);
            }
            if (Validator.valid(ids)) {
                map.put("ids", ids);
            }
        }
        List<TclassJoinVo> tclassJoinVos = tclassMapper.selectInfoEnclosureList(map);
        FileExcleUtil.exportExcel(tclassJoinVos, "班级管理", "班级信息", TclassJoinVo.class, "班级管理" + DateUtil.timeStamp2Date(DateUtil.timeStamp(), "yyyy-MM-dd") + ".xls", response);
    }

    @Override
    public void exportJoinChild(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        if (!isExportAll) {
            if (Validator.valid(queryCriteria)) {
                map.put("queryCriteria", queryCriteria);
            }
            if (Validator.valid(ids)) {
                map.put("ids", ids);
            }
        }
        List<TclassJoinVo> tclassJoinVos = tclassMapper.selectInfoJoinChildEnclosureList(map);
        FileExcleUtil.exportExcel(tclassJoinVos, "班级管理", "班级信息", TclassJoinVo.class, "班级管理" + DateUtil.timeStamp2Date(DateUtil.timeStamp(), "yyyy-MM-dd") + ".xls", response);
    }

    @Override
    public void exportExcleTemplate(String queryCriteria, Long[] ids, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        if (Validator.valid(queryCriteria)) {
            map.put("queryCriteria", queryCriteria);
        }
        if (Validator.valid(ids)) {
            map.put("ids", ids);
        }
        List<TclassJoinVo> tclassJoinVos = tclassMapper.selectInfoJoinChildEnclosureList(map);

        List<Map<String, Object>> maps = BeanCollectionInterconversionUtil.bean2map(tclassJoinVos);
        if (maps.size() != 1) {
            return;
        }
        String finalPath = oldFilePath + "班级管理" + DateUtil.timeStamp2Date(DateUtil.timeStamp(), "yyyy-MM-dd") + "\\班级管理_" + IdUtil.generateId() + ".xls";
        FileExcleUtil.exportExcelTemplate(maps.get(0),"templates/easytemplates/班级管理模板.xls",finalPath,response);

    }


}
