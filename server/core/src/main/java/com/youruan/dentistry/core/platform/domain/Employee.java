
package com.youruan.dentistry.core.platform.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 员工
 * 
 */
public class Employee
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 用户名
     * 
     */
    protected String username;
    /**
     * 姓名
     * 
     */
    protected String realName;
    /**
     * 密码
     * 
     */
    protected String password;
    /**
     * 联系电话
     * 
     */
    protected String phoneNumber;
    /**
     * 是否已锁定
     * 
     */
    protected Boolean locked;

    /**
     * 返回用户名
     * 
     * @return
     *     用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置用户名
     * 
     * @param username
     *     用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 返回姓名
     * 
     * @return
     *     姓名
     */
    public String getRealName() {
        return this.realName;
    }

    /**
     * 设置姓名
     * 
     * @param realName
     *     姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 返回密码
     * 
     * @return
     *     密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     * 
     * @param password
     *     密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 返回联系电话
     * 
     * @return
     *     联系电话
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * 设置联系电话
     * 
     * @param phoneNumber
     *     联系电话
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 返回是否已锁定
     * 
     * @return
     *     是否已锁定
     */
    public Boolean getLocked() {
        return this.locked;
    }

    /**
     * 设置是否已锁定
     * 
     * @param locked
     *     是否已锁定
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
