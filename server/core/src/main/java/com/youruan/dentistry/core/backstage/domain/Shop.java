package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 门店
 */
@Setter
@Getter
public class Shop extends BasicDomain {
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店地址
     */
    private String address;
    /**
     * 门店手机
     */
    private String phone;
    /**
     * 可预约次数
     */
    private Integer validNum;
    /**
     * 已预约次数
     */
    private Integer appointNum;
    /**
     * 门店状态
     */
    private Boolean enabled;

}
