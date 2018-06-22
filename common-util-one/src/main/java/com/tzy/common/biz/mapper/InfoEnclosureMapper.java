package com.tzy.common.biz.mapper;

import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.model.InfoEnclosure;
import com.tzy.common.lib.MyBaseMapper;

import java.util.List;

public interface InfoEnclosureMapper extends MyBaseMapper<InfoEnclosure> {

    void createList(List<InfoEnclosure> infoEnclosures);

    List<Enclosure> selectByInfo(Long infoId);
}