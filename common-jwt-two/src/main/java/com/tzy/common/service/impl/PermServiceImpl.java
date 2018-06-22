package com.tzy.common.service.impl;


import com.tzy.common.service.PermService;
import com.tzy.common.sys.mapper.PermMapper;
import com.tzy.common.sys.mapper.RolePermMapper;
import com.tzy.common.sys.model.Perm;
import com.tzy.common.sys.model.RolePerm;
import com.tzy.common.sys.model.vo.RolePermVo;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    private PermMapper permMapper;

    @Autowired
    private RolePermMapper rolePermMapper;

    @Override
    public List<Perm> findPermByUserId(Long employeeId) {
        List<Perm> permByUserId = permMapper.selectPermList(employeeId, null);
        return permByUserId;
    }

    @Override
    public List<Perm> selectUserPerm(Long employeeId) {
        List<Perm> perms = selectPerm(employeeId, 0L);
        selectUserPerm(perms, employeeId);
        return perms;
    }

    @Override
    public Perm create(Perm perm) {
        perm.setPermId(IdUtil.generateId());
        perm.setIsDel(false);
        perm.setIsDisabled(false);
        permMapper.insert(perm);
        return perm;
    }

    @Override
    public void update(Perm perm) {
        permMapper.updateByPrimaryKey(perm);
    }

    @Override
    @Transactional
    public void updateUserRoles(RolePermVo rolePermVo) {
        // 先删除原有的权限
        Long roleId = rolePermVo.getRoleId();
        Example example = new Example(RolePerm.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        rolePermMapper.deleteByExample(example);
        // 添加新的权限
        rolePermMapper.addPerms(roleId,rolePermVo.getPermList());

    }

    @Override
    public List<RolePerm> getRolePerms(Long roleId) {
        Example example = new Example(RolePerm.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<RolePerm> rolePerms = rolePermMapper.selectByExample(example);
        return rolePerms;
    }

    private void selectUserPerm(List<Perm> perms, Long employeeId) {
        perms.forEach(perm -> {
            List<Perm> children = selectPerm(employeeId, perm.getPermId());
            perm.setPermList(children);
            selectUserPerm(children, employeeId);
        });
    }

    private List<Perm> selectPerm(Long employeeId, Long parentId) {
        List<Perm> perms = permMapper.selectPermList(employeeId, parentId);
        return perms;
    }


}
