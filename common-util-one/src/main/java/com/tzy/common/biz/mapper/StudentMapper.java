package com.tzy.common.biz.mapper;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.model.Student;
import com.tzy.common.biz.vo.JoinVo.StudentJoinVo;
import com.tzy.common.lib.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends MyBaseMapper<Student> {
    List<Student> findAllLike(CommonSelectDto commonSelectDto);

    void addList(List<Student> list);

    void updateList(List<Student> list);

    List<StudentJoinVo> selectInfoEnclosureList(Map<String, Object> map);

}