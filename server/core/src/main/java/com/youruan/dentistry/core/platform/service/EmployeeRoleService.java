
package com.youruan.dentistry.core.platform.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.domain.EmployeeRole;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.EmployeeRoleQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployeeRole;

import java.util.List;

public interface EmployeeRoleService {


    public EmployeeRole get(Long id);

    public ExtendedEmployeeRole queryOne(EmployeeRoleQuery qo);

    public List<ExtendedEmployeeRole> list(EmployeeRoleQuery qo);

    public Pagination<ExtendedEmployeeRole> query(EmployeeRoleQuery qo);

    public int count(EmployeeRoleQuery qo);

    void assign(Employee employee, Role... roles);

    void unassign(Employee employee, Role... roles);

    boolean hasRole(Employee employee, Role role);
}
