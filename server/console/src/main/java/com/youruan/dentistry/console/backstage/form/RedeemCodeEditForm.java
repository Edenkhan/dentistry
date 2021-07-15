package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedeemCodeEditForm {
    private Long id;
    private String productId;
    private String shopId;
    private Integer amount;
}
