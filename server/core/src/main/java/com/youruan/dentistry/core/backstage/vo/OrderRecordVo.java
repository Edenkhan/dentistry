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
    private Long shopId;
    private Long productId;
    private String productName;
    private Integer productType;
    private Integer userType;
    private String shopName;
    private String realName;
    private String phoneNumber;
    private Integer appointStatus;
    private Integer payStatus;
    private Integer totalNum;
    private Integer appointNum;
}
