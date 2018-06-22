package com.tzy.common.sys.mapper;


import com.tzy.common.lib.MyBaseMapper;
import com.tzy.common.sys.model.RolePerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermMapper extends MyBaseMapper<RolePerm> {
    void addPerms(@Param(value = "roleId") Long roleId, @Param(value = "permList") List<String> permList);
}