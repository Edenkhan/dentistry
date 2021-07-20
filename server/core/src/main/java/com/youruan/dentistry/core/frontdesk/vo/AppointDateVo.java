package com.youruan.dentistry.core.frontdesk.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppointDateVo {
    private Date appointDate;
    private Integer timePeriod;
    private Boolean isFull;
}
