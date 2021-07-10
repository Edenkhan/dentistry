package com.youruan.dentistry.core.backstage.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AppointManageVo {
    private Integer period;
    private ExtendedAppointManage day1;
    private ExtendedAppointManage day2;
    private ExtendedAppointManage day3;
    private ExtendedAppointManage day4;
    private ExtendedAppointManage day5;
    private ExtendedAppointManage day6;
    private ExtendedAppointManage day7;
}
