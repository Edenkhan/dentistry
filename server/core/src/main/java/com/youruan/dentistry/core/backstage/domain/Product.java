package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 产品
 */
@Setter
@Getter
public class Product extends BasicDomain {
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品价格
     */
    private BigDecimal price;
    /**
     * 产品简介
     */
    private String intro;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 产品类型 0-线上 1-线下
     */
    private Integer type;
    /**
     * 产品类型【线上】
     */
    public static final Integer PRODUCT_TYPE_ONLINE = 0;
    /**
     * 产品类型【线下】
     */
    public static final Integer PRODUCT_TYPE_OFFLINE = PRODUCT_TYPE_ONLINE + 1;
    /**
     * 用户类型 0-个人 1-团体
     */
    private Integer userType;
    /**
     * 用户类型【个人】
     */
    public static final Integer USER_TYPE_INDIVIDUAL = 0;
    /**
     * 用户类型【团体】
     */
    public static final Integer USER_TYPE_TEAM = USER_TYPE_INDIVIDUAL + 1;
    /**
     * 状态 0-下架 1-销售中 2-待发布
     */
    private Integer state;
    /**
     * 状态【下架】
     */
    public static final Integer PRODUCT_STATE_OFFSHELF = 0;
    /**
     * 状态【销售中】
     */
    public static final Integer PRODUCT_STATE_SALE = PRODUCT_STATE_OFFSHELF + 1;
    /**
     * 状态【待发布】
     */
    public static final Integer PRODUCT_STATE_TO_BE_RELEASED = PRODUCT_STATE_SALE + 1;
    /**
     * 包含人数
     */
    private Integer peopleNum;
    /**
     * 包含次数
     */
    private Integer totalAppointNum;
    /**
     * 列表图片
     */
    private String iconPath;
    /**
     * 销量
     */
    private Integer sales;
}
