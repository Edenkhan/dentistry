
package com.youruan.dentistry.core.platform.service.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.domain.EmployeeRole;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.mapper.EmployeeRoleMapper;
import com.youruan.dentistry.core.platform.query.EmployeeRoleQuery;
import com.youruan.dentistry.core.platform.service.EmployeeRoleService;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployeeRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("employeeRoleService")
public class BasicEmployeeRoleService
    implements EmployeeRoleService
{

    private EmployeeRoleMapper employeeRoleMapper;


    @Autowired
    public void setEmployeeRoleMapper(EmployeeRoleMapper employeeRoleMapper) {
        this.employeeRoleMapper = employeeRoleMapper;
    }

    @Override
    public EmployeeRole get(Long id) {
        return employeeRoleMapper.get(id);
    }

    protected void update(EmployeeRole employeeRole) {
        int affected = employeeRoleMapper.update(employeeRole);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        employeeRole.setVersion((employeeRole.getVersion()+ 1));
    }

    protected EmployeeRole add(EmployeeRole employeeRole) {
        employeeRole.setCreatedDate(new Date());
        employeeRoleMapper.add(employeeRole);
        return employeeRole;
    }

    @Override
    public List<ExtendedEmployeeRole> list(EmployeeRoleQuery qo) {
        return employeeRoleMapper.query(qo);
    }

    @Override
    public ExtendedEmployeeRole queryOne(EmployeeRoleQuery qo) {
        qo.setPageSize(1);
        List<ExtendedEmployeeRole> datas = employeeRoleMapper.query(qo);
        return (((datas == null)||datas.isEmpty())?null:datas.get(0));
    }

    @Override
    public Pagination<ExtendedEmployeeRole> query(EmployeeRoleQuery qo) {
        int rows = employeeRoleMapper.count(qo);
        List<ExtendedEmployeeRole> datas = ((rows == 0)?new ArrayList<ExtendedEmployeeRole>():employeeRoleMapper.query(qo));
        return new Pagination<ExtendedEmployeeRole>(rows, datas);
    }

    @Override
    public int count(EmployeeRoleQuery qo) {
        return employeeRoleMapper.count(qo);
    }

    @Transactional
    @Override
    @CacheEvict(value = "permissions", key = "#employee.username")
    public void assign(Employee employee, Role... roles) {
        Preconditions.checkNotNull(employee, "必须提供员工");
        Preconditions.checkArgument(roles != null && roles.length > 0, "必须指定角色");

        List<EmployeeRole> employeeRoles = new ArrayList<>();
        for (Role role : roles) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(role.getId());
            employeeRoles.add(employeeRole);
        }
        employeeRoleMapper.batchAdd(employeeRoles);
    }

    @Transactional
    @Override
    @CacheEvict(value = "permissions", key = "#employee.username")
    public void unassign(Employee employee, Role... roles) {
        Preconditions.checkNotNull(employee, "必须提供员工");
        Preconditions.checkArgument(roles != null && roles.length > 0, "必须指定角色");

        for (Role role : roles) {
            unassign(employee, role);
        }
    }

    @Override
    public boolean hasRole(Employee employee, Role role) {
        EmployeeRoleQuery qo = new EmployeeRoleQuery();
        qo.setEmployeeId(employee.getId());
        qo.setRoleId(role.getId());
        return count(qo) > 0;
    }

    private void unassign(Employee employee, Role role) {
        EmployeeRoleQuery qo = new EmployeeRoleQuery();
        qo.setEmployeeId(employee.getId());
        qo.setRoleId(role.getId());
        EmployeeRole employeeRole = queryOne(qo);

        if (employeeRole == null) {
            return;
        }
        employeeRoleMapper.delete(employeeRole.getId());
    }

}
