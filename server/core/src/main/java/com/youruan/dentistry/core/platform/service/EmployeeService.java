
package com.youruan.dentistry.core.platform.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.query.EmployeeQuery;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployee;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;

import java.util.List;

public interface EmployeeService {


    public Employee get(Long id);

    public ExtendedEmployee queryOne(EmployeeQuery qo);

    public List<ExtendedEmployee> list(EmployeeQuery qo);

    public Pagination<ExtendedEmployee> query(EmployeeQuery qo);

    public int count(EmployeeQuery qo);

    Employee getByUsername(String username);

    boolean passwordMatch(Employee employee, String plaintextPassword);

    List<ExtendedRole> listRoles(Employee e);

    List<ExtendedPermission> listPermissions(Employee e);

    List<ExtendedPermission> listPermissionsByUsername(String username);

    Employee create(String realName, String username, String plainTextPassword);

    boolean existsUsername(String adminUsername);

    Employee create(String username, String realName, String phoneNumber, Boolean locked);

    void update(Employee employee, String username, String realName, String phoneNumber, Boolean locked);

    void changePassword(Employee employee, String originalPassword, String newPassword, String confirmNewPassword);

    String resetPassword(Employee employee);

    /**
     * 改变锁定状态
     */
    void changeStatus(Employee employee, Boolean locked);
}
