package com.youruan.dentistry.console.backstage.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedeemCodeAddForm {
    private Long productId;
    private Long shopId;
    private Integer codeNum;
}
