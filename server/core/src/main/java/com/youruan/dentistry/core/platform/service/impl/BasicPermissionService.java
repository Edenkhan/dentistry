
package com.youruan.dentistry.core.platform.service.impl;

import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.mapper.PermissionMapper;
import com.youruan.dentistry.core.platform.query.PermissionQuery;
import com.youruan.dentistry.core.platform.service.PermissionService;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("permissionService")
public class BasicPermissionService
    implements PermissionService
{

    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.get(id);
    }


    protected void update(Permission permission) {
        int affected = permissionMapper.update(permission);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        permission.setVersion((permission.getVersion()+ 1));
    }

    protected Permission add(Permission permission) {
        permission.setCreatedDate(new Date());
        permissionMapper.add(permission);
        return permission;
    }

    @Override
    public List<ExtendedPermission> listAll(PermissionQuery qo) {
        return permissionMapper.query(qo);
    }

    @Override
    public ExtendedPermission queryOne(PermissionQuery qo) {
        qo.setPageSize(1);
        List<ExtendedPermission> datas = permissionMapper.query(qo);
        return (((datas == null)||datas.isEmpty())?null:datas.get(0));
    }

    @Override
    public Pagination<ExtendedPermission> query(PermissionQuery qo) {
        int rows = permissionMapper.count(qo);
        List<ExtendedPermission> datas = ((rows == 0)?new ArrayList<ExtendedPermission>():permissionMapper.query(qo));
        return new Pagination<ExtendedPermission>(rows, datas);
    }

    @Override
    public int count(PermissionQuery qo) {
        return permissionMapper.count(qo);
    }


    @Override
    @Transactional
    public Permission create(String name, String description) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        return add(permission);
    }

    @Override
    @Transactional
    public void update(Permission permission, String name, String description) {
        permission.setName(name);
        permission.setDescription(description);
        update(permission);
    }

    @Override
    public List<? extends Permission> listAll(Long[] permissionIds) {
        PermissionQuery qo = new PermissionQuery();
        qo.setPageSize(permissionIds.length);
        qo.setIds(permissionIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedPermission> listAll(Role role) {
        PermissionQuery qo = new PermissionQuery();
        qo.setMaxPageSize();
        qo.setRoleId(role.getId());
        return listAll(qo);
    }

    @Override
    public List<ExtendedPermission> listAll() {
        PermissionQuery qo = new PermissionQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public Permission getByName(String name) {
        if (name == null) {
            return null;
        }
        PermissionQuery qo = new PermissionQuery();
        qo.setName(name);
        return queryOne(qo);
    }

    @Override
    public boolean existsName(String name) {
        if (name == null) {
            return false;
        }
        PermissionQuery qo = new PermissionQuery();
        qo.setName(name);
        return count(qo) > 0;
    }

}
