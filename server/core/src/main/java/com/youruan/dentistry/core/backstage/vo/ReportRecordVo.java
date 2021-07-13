package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportRecordVo extends BasicDomain {
    private String reportNo;
    private String productName;
    private Integer productType;
    private Integer userType;
    private Boolean sync;
    private String path;
    private Date appointDate;
}
