package com.tzy.common.service;


import com.tzy.common.sys.model.Perm;
import com.tzy.common.sys.model.RolePerm;
import com.tzy.common.sys.model.vo.RolePermVo;

import java.util.List;

public interface PermService {
    List<Perm> findPermByUserId(Long employeeId);


    List<Perm> selectUserPerm(Long employeeId);

    Perm create(Perm perm);

    void update(Perm perm);

    void updateUserRoles(RolePermVo rolePermVo);

    List<RolePerm> getRolePerms(Long roleId);
}
