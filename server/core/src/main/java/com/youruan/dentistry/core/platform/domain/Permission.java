
package com.youruan.dentistry.core.platform.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 权限
 * 
 */
public class Permission
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 权限名
     * 
     */
    protected String name;
    /**
     * 描述
     * 
     */
    protected String description;

    /**
     * 返回权限名
     * 
     * @return
     *     权限名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置权限名
     * 
     * @param name
     *     权限名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回描述
     * 
     * @return
     *     描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置描述
     * 
     * @param description
     *     描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
