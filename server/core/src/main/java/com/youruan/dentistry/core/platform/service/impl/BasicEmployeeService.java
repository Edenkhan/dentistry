
package com.youruan.dentistry.core.platform.service.impl;

import com.google.common.base.Preconditions;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.password.PasswordHasher;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.ValidationUtils;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.mapper.EmployeeMapper;
import com.youruan.dentistry.core.platform.query.EmployeeQuery;
import com.youruan.dentistry.core.platform.service.EmployeeService;
import com.youruan.dentistry.core.platform.service.PermissionService;
import com.youruan.dentistry.core.platform.service.RoleService;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployee;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("employeeService")
public class BasicEmployeeService
        implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final PasswordHasher passwordHasher;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Autowired
    public BasicEmployeeService(EmployeeMapper employeeMapper, PasswordHasher passwordHasher, RoleService roleService, PermissionService permissionService) {
        this.employeeMapper = employeeMapper;
        this.passwordHasher = passwordHasher;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.get(id);
    }

    protected void update(Employee employee) {
        int affected = employeeMapper.update(employee);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        employee.setVersion((employee.getVersion() + 1));
    }

    protected Employee add(Employee employee) {
        employee.setCreatedDate(new Date());
        employeeMapper.add(employee);
        return employee;
    }

    @Override
    public List<ExtendedEmployee> list(EmployeeQuery qo) {
        return employeeMapper.query(qo);
    }

    @Override
    public ExtendedEmployee queryOne(EmployeeQuery qo) {
        qo.setPageSize(1);
        List<ExtendedEmployee> datas = employeeMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedEmployee> query(EmployeeQuery qo) {
        int rows = employeeMapper.count(qo);
        List<ExtendedEmployee> datas = ((rows == 0) ? new ArrayList<ExtendedEmployee>() : employeeMapper.query(qo));
        return new Pagination<ExtendedEmployee>(rows, datas);
    }

    @Override
    public int count(EmployeeQuery qo) {
        return employeeMapper.count(qo);
    }

    @Override
    public Employee getByUsername(String username) {
        if (username == null) {
            return null;
        }
        EmployeeQuery qo = new EmployeeQuery();
        qo.setUsername(username);
        return queryOne(qo);
    }

    @Override
    public boolean passwordMatch(Employee employee, String plaintextPassword) {
        String password = employee.getPassword();
        if (password == null) {
            return false;
        }
        return passwordHasher.match(plaintextPassword, password);
    }

    @Override
    public List<ExtendedRole> listRoles(Employee employee) {
        return roleService.listAll(employee);
    }

    @Override
    public List<ExtendedPermission> listPermissions(Employee employee) {
        List<ExtendedPermission> permissions = new ArrayList<>();
        List<ExtendedRole> roles = roleService.listAll(employee);
        for (ExtendedRole role : roles) {
            permissions.addAll(permissionService.listAll(role));
        }
        return permissions;
    }

    @Override
    @Cacheable("permissions")
    public List<ExtendedPermission> listPermissionsByUsername(String username) {
        Employee employee = getByUsername(username);
        return listPermissions(employee);
    }

    @Override
    @Transactional
    public Employee create(String realName, String username, String plainTextPassword) {
        Preconditions.checkArgument(!existsUsername(username), "用户名已经存在");
        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setRealName(realName);
        employee.setPassword(passwordHasher.hash(plainTextPassword));
        employee.setLocked(false);
        return add(employee);
    }

    @Override
    public boolean existsUsername(String username) {
        EmployeeQuery qo = new EmployeeQuery();
        qo.setLikeUsername(username);
        return count(qo) > 0;
    }

    @Override
    @Transactional
    public Employee create(String username, String realName, String phoneNumber, Boolean locked) {
        Assert.notNull(username, "必须提供用户名");
        Assert.notNull(realName, "必须提供姓名");
        Assert.isTrue(phoneNumber == null || ValidationUtils.isValidPhoneNumber(phoneNumber), "联系电话格式不正确");
        Assert.isTrue(!existsUsername(username), "用户名已经存在");

        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setRealName(realName);
        employee.setPhoneNumber(phoneNumber);
        employee.setLocked(locked);
        return add(employee);
    }

    @Override
    @Transactional
    public void update(Employee employee, String username, String realName, String phoneNumber, Boolean locked) {
        Assert.notNull(username, "必须提供用户名");
        Assert.notNull(realName, "必须提供姓名");
        Assert.isTrue(phoneNumber == null || ValidationUtils.isValidPhoneNumber(phoneNumber), "联系电话格式不正确");
        if (!employee.getUsername().equals(username)) {
            Assert.isTrue(!existsUsername(username), "用户名已经存在");
        }

        employee.setUsername(username);
        employee.setRealName(realName);
        employee.setPhoneNumber(phoneNumber);
        employee.setLocked(locked);
        update(employee);
    }

    @Override
    @Transactional
    public void changePassword(Employee employee,
                               String originalPassword,
                               String newPassword,
                               String confirmNewPassword) {
        Assert.notNull(employee, "必须提供员工");
        Assert.notNull(originalPassword, "必须提供原密码");
        Assert.notNull(newPassword, "必须提供新密码");
        Assert.isTrue(newPassword.equals(confirmNewPassword), "两次输入的新密码不一致");
        Assert.isTrue(passwordMatch(employee, originalPassword), "原密码输入不正确");

        employee.setPassword(passwordHasher.hash(newPassword));
        update(employee);
    }

    @Override
    @Transactional
    public String resetPassword(Employee employee) {
        String plainText = RandomStringUtils.randomAlphabetic(8);
        employee.setPassword(passwordHasher.hash(plainText));
        update(employee);
        return plainText;
    }

    @Override
    public void changeStatus(Employee employee, Boolean locked) {
        Assert.notNull(employee,"必须提供员工");
        Assert.notNull(employee,"必须提供锁定状态");
        employee.setLocked(locked);
        this.update(employee);
    }

}
