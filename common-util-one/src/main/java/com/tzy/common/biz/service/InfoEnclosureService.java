package com.tzy.common.biz.service;

import com.tzy.common.biz.model.Enclosure;

import java.util.List;

/**
 * @Created by Heaton on 2018/6/20.
 * @describe X
 */
public interface InfoEnclosureService {

    void create(Long enclosureId, Long[] infoIds);

    void delete(Long infoEnclosureId);

    List<Enclosure> selectInfoEnclosure(Long infoId);

    void delete(Long[] ids);
}
