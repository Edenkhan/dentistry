package com.youruan.dentistry.console.user.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class BoughtListForm extends ListForm<OrdersQuery> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private Long userId;
    private String productName;
    private Integer productType;
    private Integer userType;
    private Integer productState;


    public OrdersQuery buildQuery() {
        OrdersQuery qo = new OrdersQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setUserId(userId);
        qo.setLikeProductName(productName);
        qo.setProductType(productType);
        qo.setUserType(userType);
        qo.setProductState(productState);
        //已购买
        qo.setPayStatus(Orders.PAY_STATUS_PAID);

        if ("boughtTime".equals(getSortField())) {
            qo.setOrderByBoughtTime(getSortKeyword());
        }
        return qo;
    }
}
