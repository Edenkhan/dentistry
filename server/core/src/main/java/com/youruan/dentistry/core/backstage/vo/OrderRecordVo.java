package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRecordVo extends BasicDomain {
    private BigDecimal price;
    private String orderNo;
    private String productName;
    private Integer productType;
    private Integer userType;
    private String shopName;
    private String realName;
    private String phoneNumber;
}
