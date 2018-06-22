package com.tzy.common.biz.mapper;

import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.lib.MyBaseMapper;

import java.util.List;

public interface EnclosureMapper extends MyBaseMapper<Enclosure> {

    void insertEnclosureList(List<Enclosure> list);
}