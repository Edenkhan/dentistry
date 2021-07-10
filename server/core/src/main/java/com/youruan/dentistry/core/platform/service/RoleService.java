
package com.youruan.dentistry.core.platform.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.RoleQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;

import java.util.List;

public interface RoleService {


    public Role get(Long id);

    public ExtendedRole queryOne(RoleQuery qo);

    public List<ExtendedRole> listAll(RoleQuery qo);

    public Pagination<ExtendedRole> query(RoleQuery qo);

    public int count(RoleQuery qo);

    Role create(String name, String description, Permission... permissions);

    void update(Role role, String name, String description);

    List<? extends Role> listAll(Long[] roleIds);

    List<ExtendedRole> listAll(Employee employee);

    List<ExtendedRole> listAll();

    Role getByName(String name);
}
