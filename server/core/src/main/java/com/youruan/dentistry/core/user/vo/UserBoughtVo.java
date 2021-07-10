package com.youruan.dentistry.core.user.vo;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class UserBoughtVo extends BasicDomain {
    private Date boughtTime;
    private String productName;
    private Integer productType;
    private Integer userType;
    private BigDecimal price;
    private Integer totalNum;
    private Integer appointNum;
    private Integer peopleNum;
    private String dicItemName;
    private Integer productState;
}
