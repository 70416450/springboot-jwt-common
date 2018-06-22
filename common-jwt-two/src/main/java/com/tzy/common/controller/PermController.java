package com.tzy.common.controller;


import com.tzy.common.config.JsonData;
import com.tzy.common.service.PermService;
import com.tzy.common.sys.model.Perm;
import com.tzy.common.sys.model.RolePerm;
import com.tzy.common.sys.model.vo.PermVo;
import com.tzy.common.sys.model.vo.RolePermVo;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermController {
    @Autowired
    private PermService permService;

    @PostMapping
    public JsonData create(@RequestBody @Validated(AddGroup.class) PermVo permVo) {
        Perm perm = new Perm();
        BeanUtil.copyProperties(permVo, perm);
        perm = permService.create(perm);
        return JsonData.ok(perm);
    }

    @PutMapping
    public JsonData update(@RequestBody @Validated(UpdateGroup.class) PermVo permVo) {
        Perm perm = new Perm();
        BeanUtil.copyProperties(permVo, perm);
        permService.update(perm);
        return JsonData.ok();
    }

    /**
     * 修改角色权限
     */
    @PutMapping("/role/perms")
    public JsonData updateUserRoles(@RequestBody @Validated(UpdateGroup.class) RolePermVo rolePermVo) {
        permService.updateUserRoles(rolePermVo);
        return JsonData.ok();
    }

    /**
     * 查看角色拥有的权限
     */
    @GetMapping("/perms")
    public JsonData getUserRoles(@RequestParam Long roleId) {
        List<RolePerm> rolePermList =  permService.getRolePerms(roleId);
        return JsonData.ok(rolePermList);
    }
}
