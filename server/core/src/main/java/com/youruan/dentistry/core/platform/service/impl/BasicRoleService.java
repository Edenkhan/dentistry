
package com.youruan.dentistry.core.platform.service.impl;

import com.youruan.dentistry.core.platform.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.mapper.RoleMapper;
import com.youruan.dentistry.core.platform.query.RoleQuery;
import com.youruan.dentistry.core.platform.service.RolePermissionService;
import com.youruan.dentistry.core.platform.service.RoleService;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("roleService")
public class BasicRoleService
        implements RoleService {

    private final RoleMapper roleMapper;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public BasicRoleService(RoleMapper roleMapper, RolePermissionService rolePermissionService) {
        this.roleMapper = roleMapper;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public Role get(Long id) {
        return roleMapper.get(id);
    }

    protected void update(Role role) {
        int affected = roleMapper.update(role);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        role.setVersion((role.getVersion() + 1));
    }

    protected Role add(Role role) {
        role.setCreatedDate(new Date());
        roleMapper.add(role);
        return role;
    }

    @Override
    public List<ExtendedRole> listAll(RoleQuery qo) {
        return roleMapper.query(qo);
    }

    @Override
    public ExtendedRole queryOne(RoleQuery qo) {
        qo.setPageSize(1);
        List<ExtendedRole> datas = roleMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedRole> query(RoleQuery qo) {
        int rows = roleMapper.count(qo);
        List<ExtendedRole> datas = ((rows == 0) ? new ArrayList<ExtendedRole>() : roleMapper.query(qo));
        return new Pagination<ExtendedRole>(rows, datas);
    }

    @Override
    public int count(RoleQuery qo) {
        return roleMapper.count(qo);
    }

    @Transactional
    @Override
    public Role create(String name, String description, Permission... permissions) {
        Role role = create(name, description);
        if (permissions != null && permissions.length > 0) {
            rolePermissionService.assign(role, permissions);
        }
        return role;
    }

    @Override
    @Transactional
    public void update(Role role, String name, String description) {
        role.setName(name);
        role.setDescription(description);
        update(role);
    }

    @Override
    public List<? extends Role> listAll(Long[] roleIds) {
        RoleQuery qo = new RoleQuery();
        qo.setIds(roleIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedRole> listAll(Employee employee) {
        RoleQuery qo = new RoleQuery();
        qo.setEmployeeId(employee.getId());
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public List<ExtendedRole> listAll() {
        RoleQuery qo = new RoleQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public Role getByName(String name) {
        if (name == null) {
            return null;
        }
        RoleQuery qo = new RoleQuery();
        qo.setName(name);
        return queryOne(qo);
    }

    private Role create(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return add(role);
    }
}
