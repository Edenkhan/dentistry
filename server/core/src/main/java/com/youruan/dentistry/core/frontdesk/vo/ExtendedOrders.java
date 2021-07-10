
package com.youruan.dentistry.core.frontdesk.vo;

import com.youruan.dentistry.core.frontdesk.domain.Orders;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExtendedOrders
    extends Orders
{

    private final static long serialVersionUID = 1L;
    private String productName;
    private Integer productType;
    private Integer userType;
    private Integer peopleNum;
    private String description;

}
