package com.tzy.common.biz.mapper;

import com.tzy.common.biz.model.Tclass;
import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.vo.JoinVo.TclassJoinVo;
import com.tzy.common.lib.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TclassMapper extends MyBaseMapper<Tclass> {
    List<Tclass> findAllLike(CommonSelectDto commonSelectDto);

    void addList(List<Tclass> list);

    void updateList(List<Tclass> list);

    List<Tclass> findAllLikeJoinStudent(CommonSelectDto commonSelectDto);

    List<TclassJoinVo> selectInfoEnclosureList(Map<String, Object> map);

    List<TclassJoinVo> selectInfoJoinChildEnclosureList(Map<String, Object> map);
}