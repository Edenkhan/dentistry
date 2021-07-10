package com.youruan.dentistry.portal.frontdesk.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AppointmentAddForm {
    private Integer timePeriod;
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private Date appointDate;
    private Long orderId;
}
