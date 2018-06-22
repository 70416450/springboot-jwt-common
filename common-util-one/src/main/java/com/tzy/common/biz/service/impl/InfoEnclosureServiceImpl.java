package com.tzy.common.biz.service.impl;

import com.tzy.common.biz.mapper.InfoEnclosureMapper;
import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.model.InfoEnclosure;
import com.tzy.common.biz.service.InfoEnclosureService;
import com.tzy.common.exception.BizErrorCode;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.util.Validator;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Created by Heaton on 2018/6/20.
 * @describe X
 */
@Service
public class InfoEnclosureServiceImpl implements InfoEnclosureService {
    @Autowired
    private InfoEnclosureMapper infoEnclosureMapper;

    @Override
    public void create(Long enclosureId, Long[] infoIds) {
        List<InfoEnclosure> infoEnclosures = new ArrayList<>();
        for (Long infoId : infoIds) {
            InfoEnclosure infoEnclosure = InfoEnclosure.builder().bizEnclosureId(IdUtil.generateId())
                    .enclosureId(enclosureId).infoId(infoId).isDel(false).build();
            infoEnclosures.add(infoEnclosure);
        }
        infoEnclosureMapper.createList(infoEnclosures);
    }


    @Override
    public void delete(Long infoEnclosureId) {
        InfoEnclosure infoEnclosure = InfoEnclosure.builder().bizEnclosureId(infoEnclosureId).
                isDel(true).build();
        infoEnclosureMapper.updateByPrimaryKeySelective(infoEnclosure);
    }

    @Override
    public List<Enclosure> selectInfoEnclosure(Long infoId) {
        return infoEnclosureMapper.selectByInfo(infoId);
    }

    @Override
    public void delete(Long[] ids) {
        Example example = getExample(ids);
        InfoEnclosure infoEnclosure = InfoEnclosure.builder().isDel(true).build();
        infoEnclosureMapper.updateByExampleSelective(infoEnclosure, example);
    }

    //条件 批量删除使用
    public Example getExample(Long[] ids) {
        if (!Validator.valid(ids)) {
            throw new BusinessException(BizErrorCode.ID_NULL);
        }
        List<Long> idss = Arrays.asList(ids);
        Example example = new Example(InfoEnclosure.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("enclosureId", idss);
        return example;
    }
}
