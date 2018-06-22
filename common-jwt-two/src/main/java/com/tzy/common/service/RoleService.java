package com.tzy.common.service;


import com.tzy.common.sys.model.EmployeeRole;
import com.tzy.common.sys.model.Role;
import com.tzy.common.sys.model.vo.EmployeeRoleVO;

import java.util.List;

public interface RoleService {
    Role create(Role role);

    void update(Role role);

    void updateUserRoles(EmployeeRoleVO employeeRoleVO);

    List<EmployeeRole> getUserRoles(Long employeeId);
}
