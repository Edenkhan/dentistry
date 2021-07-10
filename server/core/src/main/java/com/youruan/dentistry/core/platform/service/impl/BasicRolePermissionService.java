
package com.youruan.dentistry.core.platform.service.impl;

import com.google.common.base.Preconditions;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.domain.RolePermission;
import com.youruan.dentistry.core.platform.mapper.RolePermissionMapper;
import com.youruan.dentistry.core.platform.query.RolePermissionQuery;
import com.youruan.dentistry.core.platform.service.RolePermissionService;
import com.youruan.dentistry.core.platform.vo.ExtendedRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rolePermissionService")
public class BasicRolePermissionService
        implements RolePermissionService {

    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public RolePermission get(Long id) {
        return rolePermissionMapper.get(id);
    }

    protected void update(RolePermission rolePermission) {
        int affected = rolePermissionMapper.update(rolePermission);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        rolePermission.setVersion((rolePermission.getVersion() + 1));
    }

    protected RolePermission add(RolePermission rolePermission) {
        rolePermission.setCreatedDate(new Date());
        rolePermissionMapper.add(rolePermission);
        return rolePermission;
    }

    @Override
    public List<ExtendedRolePermission> list(RolePermissionQuery qo) {
        return rolePermissionMapper.query(qo);
    }

    @Override
    public ExtendedRolePermission queryOne(RolePermissionQuery qo) {
        qo.setPageSize(1);
        List<ExtendedRolePermission> datas = rolePermissionMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedRolePermission> query(RolePermissionQuery qo) {
        int rows = rolePermissionMapper.count(qo);
        List<ExtendedRolePermission> datas = ((rows == 0) ? new ArrayList<ExtendedRolePermission>() : rolePermissionMapper.query(qo));
        return new Pagination<ExtendedRolePermission>(rows, datas);
    }

    @Override
    public int count(RolePermissionQuery qo) {
        return rolePermissionMapper.count(qo);
    }

    @Transactional
    @Override
    @CacheEvict(value = "permissions", allEntries = true)
    public void assign(Role role, Permission... permissions) {
        Preconditions.checkNotNull(role, "必须提供角色");
        Preconditions.checkArgument(permissions != null && permissions.length > 0, "必须指定权限");

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permission.getId());
            rolePermission.setRoleId(role.getId());
            rolePermissions.add(rolePermission);
        }
        rolePermissionMapper.batchAdd(rolePermissions);
    }

    @Transactional
    @Override
    @CacheEvict(value = "permissions", allEntries = true)
    public void unassign(Role role, Permission... permissions) {
        Preconditions.checkNotNull(role, "必须提供角色");
        Preconditions.checkArgument(permissions != null && permissions.length > 0, "必须指定权限");
        for (Permission permission : permissions) {
            unassign(role, permission);
        }
    }

    @Override
    public boolean hasPermission(Role role, Permission permission) {
        RolePermissionQuery qo = new RolePermissionQuery();
        qo.setRoleId(role.getId());
        qo.setPermissionId(permission.getId());
        return count(qo) > 0;
    }

    private void unassign(Role role, Permission permission) {
        RolePermissionQuery qo = new RolePermissionQuery();
        qo.setRoleId(role.getId());
        qo.setPermissionId(permission.getId());
        RolePermission rolePermission = queryOne(qo);
        if (rolePermission == null) {
            return;
        }
        rolePermissionMapper.delete(rolePermission.getId());
    }

}
