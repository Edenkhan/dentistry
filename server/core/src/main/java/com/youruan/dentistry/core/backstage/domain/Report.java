package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 报告
 */
@Getter
@Setter
public class Report extends BasicDomain {
    /**
     * 报告编号
     */
    private String reportNo;
    /**
     * 报告路径
     */
    private String path;
    /**
     * 是否同步
     */
    private Boolean sync;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 预约id
     */
    private Long appointId;
}
