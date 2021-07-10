package com.youruan.dentistry.console.platform;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.ErrorResponseEntity;
import com.youruan.dentistry.console.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.console.base.interceptor.RequiresGuest;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.base.utils.SessionUtils;
import com.youruan.dentistry.console.platform.form.*;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.EmployeeQuery;
import com.youruan.dentistry.core.platform.service.EmployeeRoleService;
import com.youruan.dentistry.core.platform.service.EmployeeService;
import com.youruan.dentistry.core.platform.service.RoleService;
import com.youruan.dentistry.core.platform.vo.ExtendedEmployee;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/platform/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final EmployeeRoleService employeeRoleService;

    public EmployeeController(EmployeeService employeeService, RoleService roleService, EmployeeRoleService employeeRoleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.employeeRoleService = employeeRoleService;
    }

    @GetMapping("/profile")
    @RequiresAuthentication
    public ResponseEntity<?> profile() {
        String username = SessionUtils.getAuthenticated();
        List<String> permissions = employeeService.listPermissionsByUsername(username)
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("username", username)
                .put("permissions", permissions)
                .build());
    }

    @PostMapping("/logout")
    @RequiresAuthentication
    public ResponseEntity<?> logout() {
        SessionUtils.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @RequiresGuest
    public ResponseEntity<?> login(@Valid LoginForm form) {
        Employee employee = employeeService.getByUsername(form.getUsername());
        if (employee == null) {
            return ErrorResponseEntity.badRequest("用户名或密码不正确");
        }
        boolean match = employeeService.passwordMatch(employee, form.getPassword());
        if (!match) {
            return ErrorResponseEntity.badRequest("用户名或密码不正确");
        }
        if (employee.getLocked()) {
            return ErrorResponseEntity.badRequest("用户已被锁定");
        }

        SessionUtils.login(employee.getUsername());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", employee.getId())
                .build());
    }

    @GetMapping("/list")
    @RequiresPermission(value = "platform.employee.list", description = "员工-列表")
    public ResponseEntity<?> list(EmployeeListForm form) {
        EmployeeQuery qo = form.buildQuery();
        Pagination<ExtendedEmployee> pagination = employeeService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "username", "realName", "phoneNumber", "locked"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "platform.employee.get", description = "员工-获取")
    public ResponseEntity<?> list(@RequestParam("id") Long id) {
        Employee employee = employeeService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(employee, "id", "username", "realName", "phoneNumber", "locked"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "platform.employee.add", description = "员工-添加")
    public ResponseEntity<?> add(EmployeeAddForm form) {
        Employee employee = employeeService.create(
                form.getUsername(),
                form.getRealName(),
                form.getPhoneNumber(),
                form.getLocked());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", employee.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "platform.employee.edit", description = "员工-编辑")
    public ResponseEntity<?> edit(EmployeeEditForm form) {
        String username = SessionUtils.getAuthenticated();
        Employee authEmployee = employeeService.getByUsername(username);

        Employee employee = employeeService.get(form.getId());
        if (authEmployee.getId().equals(employee.getId()) && form.getLocked() != null && form.getLocked()) {
            return ErrorResponseEntity.badRequest("自己不能锁定自己");
        }
        employeeService.update(
                employee,
                form.getUsername(),
                form.getRealName(),
                form.getPhoneNumber(),
                form.getLocked());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", employee.getId())
                .build());
    }

    @GetMapping("/assignRolesOptions")
    @RequiresPermission(value = "platform.employee.assignRolesOptions", description = "员工-获取分配角色选项")
    public ResponseEntity<?> assignRoleOptions(@RequestParam("id") Long id) {
        List<ExtendedRole> roles = roleService.listAll();
        Employee employee = employeeService.get(id);
        List<ExtendedRole> assignedRoles = roleService.listAll(employee);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("roles", BeanMapUtils.pick(roles, "id", "name", "description"))
                .put("assignedRoleIds", assignedRoles.stream().map(Role::getId).collect(Collectors.toList()))
                .build());
    }

    @PostMapping("/assignRoles")
    @RequiresPermission(value = "platform.employee.assignRoles", description = "员工-分配角色")
    public ResponseEntity<?> assignRoles(@RequestParam("employeeId") Long employeeId,
                                         @RequestParam(value = "assignedIds", required = false) Long[] assignedIds,
                                         @RequestParam(value = "unassignedIds", required = false) Long[] unassignedIds) {
        Employee employee = employeeService.get(employeeId);
        if (ArrayUtils.isNotEmpty(assignedIds)) {
            List<? extends Role> roles = roleService.listAll(assignedIds);
            employeeRoleService.assign(employee, roles.toArray(new Role[0]));
        }
        if (ArrayUtils.isNotEmpty(unassignedIds)) {
            List<? extends Role> roles = roleService.listAll(unassignedIds);
            employeeRoleService.unassign(employee, roles.toArray(new Role[0]));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changePassword")
    @RequiresAuthentication
    public ResponseEntity<?> changePassword(ChangePasswordForm form) {
        String username = SessionUtils.getAuthenticated();
        Employee employee = employeeService.getByUsername(username);
        employeeService.changePassword(
                employee,
                form.getOriginalPassword(),
                form.getNewPassword(),
                form.getConfirmNewPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resetPassword")
    @RequiresPermission(value = "platform.employee.resetPassword", description = "员工-重置密码")
    public ResponseEntity<?> resetPassword(@RequestParam("id") Long id) {
        Employee employee = employeeService.get(id);
        String newPassword = employeeService.resetPassword(employee);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("newPassword", newPassword)
                .build());
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<?> checkLogin() {
        boolean authenticated = SessionUtils.isAuthenticated();
        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
        if (authenticated) {
            builder.put("username", SessionUtils.getAuthenticated());
        }
        builder.put("authenticated", authenticated);
        return ResponseEntity.ok(builder.build());
    }

    @PostMapping("/changeStatus")
    @RequiresPermission(value = "platform.employee.changeStatus", description = "员工-改变状态")
    public ResponseEntity<?> changeStatus(Employee authEmployee, EmployeeEditForm form) {
        Employee employee = employeeService.get(form.getId());
        Assert.isTrue(!authEmployee.getId().equals(employee.getId()),"不能自己锁定自己");
        employeeService.changeStatus(employee,form.getLocked());
        return ResponseEntity.ok().build();
    }
}
