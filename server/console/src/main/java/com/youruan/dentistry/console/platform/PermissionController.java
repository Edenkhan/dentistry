package com.youruan.dentistry.console.platform;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.platform.form.PermissionAddForm;
import com.youruan.dentistry.console.platform.form.PermissionEditForm;
import com.youruan.dentistry.console.platform.form.PermissionListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.query.PermissionQuery;
import com.youruan.dentistry.core.platform.service.PermissionService;
import com.youruan.dentistry.core.platform.vo.ExtendedPermission;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platform/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "platform.permission.list", description = "权限-列表")
    public ResponseEntity<?> list(PermissionListForm form) {
        PermissionQuery qo = form.buildQuery();
        Pagination<ExtendedPermission> pagination = permissionService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "name", "description"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "platform.permission.get", description = "权限-获取")
    public ResponseEntity<?> list(@RequestParam("id") Long id) {
        Permission permission = permissionService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(permission, "id", "name", "description"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "platform.permission.add", description = "权限-添加")
    public ResponseEntity<?> add(PermissionAddForm form) {
        Permission permission = permissionService.create(
                form.getName(),
                form.getDescription());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", permission.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "platform.permission.edit", description = "权限-修改")
    public ResponseEntity<?> edit(PermissionEditForm form) {
        Permission permission = permissionService.get(form.getId());
        permissionService.update(
                permission,
                form.getName(),
                form.getDescription());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", permission.getId())
                .build());
    }
}
