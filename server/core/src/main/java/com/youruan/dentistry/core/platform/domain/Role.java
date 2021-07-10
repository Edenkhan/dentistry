
package com.youruan.dentistry.core.platform.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 角色
 * 
 */
public class Role
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 角色名
     * 
     */
    protected String name;
    /**
     * 描述
     * 
     */
    protected String description;

    /**
     * 返回角色名
     * 
     * @return
     *     角色名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置角色名
     * 
     * @param name
     *     角色名
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
