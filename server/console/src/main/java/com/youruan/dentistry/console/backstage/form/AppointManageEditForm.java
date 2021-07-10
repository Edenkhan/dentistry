package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointManageEditForm {
    private Long id;
    private Boolean enabled;
    private Integer topLimit;
    private Long shopId;
}
