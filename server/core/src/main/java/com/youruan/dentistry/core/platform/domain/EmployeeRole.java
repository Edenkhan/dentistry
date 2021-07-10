
package com.youruan.dentistry.core.platform.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 角色分配
 * 
 */
public class EmployeeRole
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 员工
     * 
     */
    protected Long employeeId;
    /**
     * 角色
     * 
     */
    protected Long roleId;

    /**
     * 返回员工
     * 
     * @return
     *     员工
     */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
     * 设置员工
     * 
     * @param employeeId
     *     员工
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

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

}
