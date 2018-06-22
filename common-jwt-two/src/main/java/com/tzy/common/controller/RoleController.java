package com.tzy.common.controller;


import com.tzy.common.config.JsonData;
import com.tzy.common.service.RoleService;
import com.tzy.common.sys.model.EmployeeRole;
import com.tzy.common.sys.model.Role;
import com.tzy.common.sys.model.vo.EmployeeRoleVO;
import com.tzy.common.sys.model.vo.RoleVo;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public JsonData create(@RequestBody @Validated(AddGroup.class) RoleVo roleVo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleVo, role);
        role = roleService.create(role);
        return JsonData.ok(role);
    }

    @PutMapping
    public JsonData update(@RequestBody @Validated(UpdateGroup.class) RoleVo roleVo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleVo, role);
        roleService.update(role);
        return JsonData.ok();
    }

    /**
     * 修改用户角色
     */
    @PutMapping("/user/roles")
    public JsonData updateUserRoles(@RequestBody @Validated(UpdateGroup.class) EmployeeRoleVO employeeRoleVO) {
        roleService.updateUserRoles(employeeRoleVO);
        return JsonData.ok();
    }

    /**
     * 查看用户拥有的角色
     */
    @GetMapping("/roles")
    public JsonData getUserRoles(@RequestParam Long employeeId) {
        List<EmployeeRole> employeeRoleList =  roleService.getUserRoles(employeeId);
        return JsonData.ok(employeeRoleList);
    }

}
