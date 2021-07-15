package com.youruan.dentistry.core.frontdesk.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Appointment extends BasicDomain {
    /**
     * 到店时间
     */
    private Date arrivedDate;
    /**
     * 预约日期
     */
    private Date appointDate;
    /**
     * 预约时间段
     */
    private Integer timePeriod;
    /**
     * 预约时间段 【上午】
     */
    public static final Integer PERIOD_AM = 0;
    /**
     * 预约时间段 【下午】
     */
    public static final Integer PERIOD_PM = PERIOD_AM + 1;
    /**
     * 预约状态
     */
    private Integer appointState;
    /**
     * 预约状态 【预约中】
     */
    public static final Integer APPOINT_STATE_APPOINTED = 0;
    /**
     * 预约状态 【预约完成】
     */
    public static final Integer APPOINT_STATE_FINISH = APPOINT_STATE_APPOINTED + 1;
    /**
     * 报告状态
     */
    private Integer reportStatus;
    /**
     * 报告状态 【未上传】
     */
    public static final Integer REPORT_STATUS_NOT = 0;
    /**
     * 报告状态 【已上传】
     */
    public static final Integer REPORT_STATUS_OK = REPORT_STATUS_NOT + 1;
    /**
     * 订单id
     */
    private Long orderId;
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
}
