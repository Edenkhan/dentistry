
package com.youruan.dentistry.core.platform.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.PermissionQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;

import java.util.List;

public interface PermissionService {


    public Permission get(Long id);

    public ExtendedPermission queryOne(PermissionQuery qo);

    public List<ExtendedPermission> listAll(PermissionQuery qo);

    public Pagination<ExtendedPermission> query(PermissionQuery qo);

    public int count(PermissionQuery qo);

    Permission create(String name, String description);

    void update(Permission permission, String name, String description);

    List<? extends Permission> listAll(Long[] permissionIds);

    List<ExtendedPermission> listAll(Role role);

    List<ExtendedPermission> listAll();

    Permission getByName(String name);

    boolean existsName(String name);
}
