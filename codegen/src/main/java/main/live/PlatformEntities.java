package main.live;

import main.AbstractEntities;
import mate.Entity;
import mate.Property;

public class PlatformEntities extends AbstractEntities {

    static Entity Employee;

    public PlatformEntities() {
        Entity RolePermission = RolePermission();
        Entity Permission = Permission(RolePermission);
        Entity EmployeeRole = EmployeeRole();
        Employee = Employee();
        addEntity(Employee);
        addEntity(Permission);
        addEntity(EmployeeRole);
        addEntity(Role(EmployeeRole));
        addEntity(RolePermission);
    }

    private Entity Employee() {
        Entity entity = new Entity("Employee", "e", "员工");
        entity.setProperties(
                new Property("String", "username", "用户名").setUpdate(true).setLikeSearchable(true).setUniqueIndex(true).setSearchable(true),
                new Property("String", "realName", "姓名").setUpdate(true).setLikeSearchable(true),
                new Property("String", "password", "密码").setUpdate(true),
                new Property("String", "phoneNumber", "联系电话").setUpdate(true).setSearchable(true).setLikeSearchable(true),
                new Property("Boolean", "locked", "是否已锁定").setUpdate(true).setSearchable(true));
        return entity;
    }

    private Entity Permission(Entity RolePermission) {
        Entity entity = new Entity("Permission", "p", "权限");
        entity.setMappingSearch("roleId", RolePermission, "permissionId");
        entity.setProperties(
                new Property("String", "name", "权限名").setUpdate(true).setLikeSearchable(true).setUniqueIndex(true).setSearchable(true),
                new Property("String", "description", "描述").setUpdate(true));
        return entity;
    }

    private Entity Role(Entity EmployeeRole) {
        Entity entity = new Entity("Role", "r", "角色");
        entity.setMappingSearch("employeeId", EmployeeRole, "roleId");
        entity.setProperties(
                new Property("String", "name", "角色名").setUpdate(true).setLikeSearchable(true).setSearchable(true),
                new Property("String", "description", "描述").setUpdate(true));
        return entity;
    }

    private Entity RolePermission() {
        Entity entity = new Entity("RolePermission", "rp", "权限分配");
        entity.setBatchInsert(true);
        entity.setProperties(
                new Property("Long", "roleId", "角色").setSearchable(true).setUniqueIndex(true, "permissionId").setSearchable(true),
                new Property("Long", "permissionId", "权限").setSearchable(true));
        return entity;
    }

    private Entity EmployeeRole() {
        Entity entity = new Entity("EmployeeRole", "er", "角色分配");
        entity.setBatchInsert(true);
        entity.setProperties(
                new Property("Long", "employeeId", "员工").setSearchable(true).setUniqueIndex(true, "roleId"),
                new Property("Long", "roleId", "角色").setSearchable(true));
        return entity;
    }

    @Override
    public String getModuleName() {
        return "platform";
    }
}
