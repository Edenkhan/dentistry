package com.youruan.dentistry.console.user.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditForm {

    private Long id;
    private String phoneNumber;
    private Boolean locked;

}
