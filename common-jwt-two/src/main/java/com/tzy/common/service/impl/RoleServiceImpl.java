package com.tzy.common.service.impl;


import com.tzy.common.service.RoleService;
import com.tzy.common.sys.mapper.EmployeeRoleMapper;
import com.tzy.common.sys.mapper.RoleMapper;
import com.tzy.common.sys.model.EmployeeRole;
import com.tzy.common.sys.model.Role;
import com.tzy.common.sys.model.vo.EmployeeRoleVO;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Override
    public Role create(Role role) {
        role.setRoleId(IdUtil.generateId());
        role.setIsDel(false);
        role.setIsDisabled(false);
        roleMapper.insert(role);
        return role;
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    @Transactional
    public void updateUserRoles(EmployeeRoleVO employeeRoleVO) {
        // 先删除原有的角色
        Long employeeId = employeeRoleVO.getEmployeeId();
        Example example = new Example(EmployeeRole.class);
        example.createCriteria().andEqualTo("employeeId", employeeId);
        employeeRoleMapper.deleteByExample(example);
        // 添加新的角色
        employeeRoleMapper.addRoles(employeeId,employeeRoleVO.getRoleList());
    }

    @Override
    public List<EmployeeRole> getUserRoles(Long employeeId) {
        Example example = new Example(EmployeeRole.class);
        example.createCriteria().andEqualTo("employeeId", employeeId);
        List<EmployeeRole> employeeRoles = employeeRoleMapper.selectByExample(example);
        return employeeRoles;
    }
}
