
package com.youruan.dentistry.core.platform.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 权限分配
 * 
 */
public class RolePermission
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 角色
     * 
     */
    protected Long roleId;
    /**
     * 权限
     * 
     */
    protected Long permissionId;

    /**
     * 返回角色
     * 
     * @return
     *     角色
     */
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * 设置角色
     * 
     * @param roleId
     *     角色
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 返回权限
     * 
     * @return
     *     权限
     */
    public Long getPermissionId() {
        return this.permissionId;
    }

    /**
     * 设置权限
     * 
     * @param permissionId
     *     权限
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

}
