package com.youruan.dentistry.portal.frontdesk.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrdersAddForm {
    private BigDecimal price;
    private Long userId;
    private Long productId;
    private Long shopId;
    private Long dicItemId;
}
