package com.youruan.dentistry.core.frontdesk.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Orders extends BasicDomain {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 总次数
     */
    private Integer totalNum;
    /**
     * 已预约次数
     */
    private Integer appointNum;
    /**
     * 购买时间
     */
    private Date boughtTime;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 订单状态【未支付】
     */
    public static final Integer PAY_STATUS_UNPAID = 0;
    /**
     * 订单状态【已支付】
     */
    public static final Integer PAY_STATUS_PAID = PAY_STATUS_UNPAID + 1;
    /**
     * 预约状态
     */
    private Integer appointStatus;
    /**
     * 预约状态【待预约】
     */
    public static final Integer APPOINT_STATUS_NOT = 0;
    /**
     * 预约状态【已预约】
     */
    public static final Integer APPOINT_STATUS_OK = APPOINT_STATUS_NOT + 1;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 门店id
     */
    private Long shopId;
    /**
     * 字典详情id
     */
    private Long dicItemId;
}
