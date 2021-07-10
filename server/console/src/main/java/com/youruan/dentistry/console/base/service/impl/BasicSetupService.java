package com.youruan.dentistry.console.base.service.impl;

import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.base.service.SetupService;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.service.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Map;

@Service
public class BasicSetupService implements SetupService, ApplicationContextAware {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final EmployeeRoleService employeeRoleService;
    private final RolePermissionService rolePermissionService;
    private ApplicationContext applicationContext;

    public BasicSetupService(EmployeeService employeeService, RoleService roleService, PermissionService permissionService, EmployeeRoleService employeeRoleService, RolePermissionService rolePermissionService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.employeeRoleService = employeeRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    @Transactional
    public void setup() {
        String username = "dentistry";
        Employee employee = employeeService.getByUsername(username);
        if (employee == null) {
            employee = employeeService.create("超级管理员", username, "admin");
        }

        // 角色与权限
        Role role = roleService.getByName("超级管理员");
        if (role == null) {
            role = roleService.create("超级管理员", "拥有全部权限");
        }
        Permission permission = permissionService.getByName("*");
        if (permission == null) {
            permission = permissionService.create("*", "全部权限");
        }
        if (!employeeRoleService.hasRole(employee, role)) {
            employeeRoleService.assign(employee, role);
        }
        if (!rolePermissionService.hasPermission(role, permission)) {
            rolePermissionService.assign(role, permission);
        }

        // 权限
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        controllers.forEach((beanName, bean) -> {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                RequiresPermission annotation = method.getAnnotation(RequiresPermission.class);
                if (annotation == null) {
                    continue;
                }
                String name = annotation.value();
                String description = annotation.description();
                if (!permissionService.existsName(name)) {
                    permissionService.create(name, description);
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
