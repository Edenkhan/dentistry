package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 预约管理
 */
@Setter
@Getter
@ToString
public class AppointManage extends BasicDomain {
    /**
     * 预约上限
     */
    private Integer topLimit;
    /**
     * 默认预约上限
     */
    public static final Integer DEFAULT_TOPLIMIT = 50;
    /**
     * 已预约次数
     */
    private Integer appointNum;
    /**
     * 预约日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointDate;
    /**
     * 预约时间段 0-am 1-pm
     */
    private Integer timePeriod;
    /**
     * 预约时间段 【上午】
     */
    public static final Integer TIME_PERIOD_AM = 0;
    /**
     * 预约时间段 【下午】
     */
    public static final Integer TIME_PERIOD_PM = TIME_PERIOD_AM + 1;
    /**
     * 是否开启预约
     */
    private Boolean enabled;
    /**
     * 门店id
     */
    private Long shopId;
}
