package com.tzy.common.sys.mapper;


import com.tzy.common.lib.MyBaseMapper;
import com.tzy.common.sys.model.Perm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermMapper extends MyBaseMapper<Perm> {
    List<Perm> selectPermList(@Param("employeeId") Long employeeId, @Param("parentId") Long parentId);
}