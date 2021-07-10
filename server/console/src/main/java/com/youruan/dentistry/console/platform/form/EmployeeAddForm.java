package com.youruan.dentistry.console.platform.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAddForm {
    private String username;
    private String realName;
    private String phoneNumber;
    private Boolean locked;
}
