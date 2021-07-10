package com.youruan.dentistry.console.backstage.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class OrderRecordListForm extends ListForm<OrdersQuery> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String orderNo;
    private String realName;
    private String phoneNumber;
    private String shopName;
    private Integer productType;
    private Integer userType;

    public OrdersQuery buildQuery() {
        OrdersQuery qo = new OrdersQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikeOrderNo(orderNo);
        qo.setLikeRealName(realName);
        qo.setLikePhoneNumber(phoneNumber);
        qo.setLikeShopName(shopName);
        qo.setProductType(productType);
        qo.setUserType(userType);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortOrder().equalsIgnoreCase("descend")
                    ? QueryCondition.ORDER_BY_KEYWORD_DESC
                    : QueryCondition.ORDER_BY_KEYWORD_ASC);
        }
        return qo;
    }
}
