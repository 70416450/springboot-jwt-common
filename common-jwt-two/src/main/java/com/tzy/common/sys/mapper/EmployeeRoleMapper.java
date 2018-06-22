package com.tzy.common.sys.mapper;

import com.tzy.common.lib.MyBaseMapper;
import com.tzy.common.sys.model.EmployeeRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeRoleMapper extends MyBaseMapper<EmployeeRole> {
    void addRoles(@Param(value = "employeeId") Long employeeId, @Param(value = "roleList") List<String> roleList);
}