
package com.youruan.dentistry.core.platform.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.domain.RolePermission;
import com.youruan.dentistry.core.platform.query.RolePermissionQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedRolePermission;

import java.util.List;

public interface RolePermissionService {


    public RolePermission get(Long id);

    public ExtendedRolePermission queryOne(RolePermissionQuery qo);

    public List<ExtendedRolePermission> list(RolePermissionQuery qo);

    public Pagination<ExtendedRolePermission> query(RolePermissionQuery qo);

    public int count(RolePermissionQuery qo);

    void assign(Role role, Permission... permissions);

    void unassign(Role role, Permission... permissions);

    boolean hasPermission(Role role, Permission permission);
}
