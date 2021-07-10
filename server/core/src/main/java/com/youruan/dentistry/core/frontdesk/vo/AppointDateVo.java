package com.youruan.dentistry.core.frontdesk.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointDateVo {
    private Date appointDate;
    private Integer timePeriod;
    private Boolean full;
}
