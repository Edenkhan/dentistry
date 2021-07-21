package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopEditForm {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Integer frequency;
    private Boolean enabled;
}
