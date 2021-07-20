package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointManageEditForm {
    private Long id;
    private Integer timePeriod;
    private Boolean enabled;
    private Integer topLimit;
    private Long shopId;
    private Integer amTopLimit;
    private Integer pmTopLimit;
}
