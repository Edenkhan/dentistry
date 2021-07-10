package com.youruan.dentistry.console.platform;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.platform.form.RoleAddForm;
import com.youruan.dentistry.console.platform.form.RoleEditForm;
import com.youruan.dentistry.console.platform.form.RoleListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.domain.Role;
import com.youruan.dentistry.core.platform.query.RoleQuery;
import com.youruan.dentistry.core.platform.service.PermissionService;
import com.youruan.dentistry.core.platform.service.RolePermissionService;
import com.youruan.dentistry.core.platform.service.RoleService;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import com.youruan.dentistry.core.platform.vo.ExtendedRole;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/platform/role")
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;

    public RoleController(RoleService roleService, PermissionService permissionService, RolePermissionService rolePermissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "platform.role.list", description = "角色-列表")
    public ResponseEntity<?> list(RoleListForm form) {
        RoleQuery qo = form.buildQuery();
        Pagination<ExtendedRole> pagination = roleService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "name", "description"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "platform.role.get", description = "角色-获取")
    public ResponseEntity<?> list(@RequestParam("id") Long id) {
        Role role = roleService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(role, "id", "name", "description"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "platform.role.add", description = "角色-添加")
    public ResponseEntity<?> add(RoleAddForm form) {
        Role role = roleService.create(
                form.getName(),
                form.getDescription());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", role.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "platform.role.edit", description = "角色-修改")
    public ResponseEntity<?> edit(RoleEditForm form) {
        Role role = roleService.get(form.getId());
        roleService.update(
                role,
                form.getName(),
                form.getDescription());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", role.getId())
                .build());
    }

    @GetMapping("/assignPermissionsOptions")
    @RequiresPermission(value = "platform.role.assignPermissionsOptions", description = "角色-获取分配权限选项")
    public ResponseEntity<?> assignPermissionOptions(@RequestParam("id") Long id) {
        List<ExtendedPermission> permissions = permissionService.listAll();
        Role role = roleService.get(id);
        List<ExtendedPermission> assignedPermissions = permissionService.listAll(role);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("permissions", BeanMapUtils.pick(permissions, "id", "name", "description"))
                .put("assignedPermissionIds", assignedPermissions.stream().map(Permission::getId).collect(Collectors.toList()))
                .build());
    }

    @PostMapping("/assignPermissions")
    @RequiresPermission(value = "platform.role.assignPermissions", description = "角色-分配权限")
    public ResponseEntity<?> assignPermissions(@RequestParam("roleId") Long roleId,
                                               @RequestParam(value = "assignedIds", required = false) Long[] assignedIds,
                                               @RequestParam(value = "unassignedIds", required = false) Long[] unassignedIds) {
        Role role = roleService.get(roleId);
        if (ArrayUtils.isNotEmpty(assignedIds)) {
            List<? extends Permission> permissions = permissionService.listAll(assignedIds);
            rolePermissionService.assign(role, permissions.toArray(new Permission[0]));
        }
        if (ArrayUtils.isNotEmpty(unassignedIds)) {
            List<? extends Permission> permissions = permissionService.listAll(unassignedIds);
            rolePermissionService.unassign(role, permissions.toArray(new Permission[0]));
        }
        return ResponseEntity.ok().build();
    }
}
