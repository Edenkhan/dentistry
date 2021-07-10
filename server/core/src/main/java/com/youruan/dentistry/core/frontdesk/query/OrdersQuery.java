
package com.youruan.dentistry.core.frontdesk.query;

import com.youruan.dentistry.core.base.query.QueryCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class OrdersQuery
    extends QueryCondition
{

    protected Long[] ids;
    protected Date startCreatedDate;
    protected Date endCreatedDate;
    protected String orderNo;
    protected String likeOrderNo;
    protected Long userId;
    protected Integer payStatus;
    protected Integer appointStatus;

    protected String realName;
    protected String likeRealName;
    private String phoneNumber;
    private String likePhoneNumber;
    private String shopName;
    private String likeShopName;
    private String productName;
    private String likeProductName;
    private Integer productType;
    private Integer userType;
    private Integer productState;

    public boolean hasIds() {
        return (this.ids.length!= 0);
    }

    public void setOrderById(int keyword) {
        setOrderBy("id", keyword);
    }

    public int getOrderById() {
        return getOrderByKeyword("id");
    }

    public void setOrderByCreatedDate(int keyword) {
        setOrderBy("createdDate", keyword);
    }

    public int getOrderByCreatedDate() {
        return getOrderByKeyword("createdDate");
    }

    public void setOrderByBoughtTime(int keyword) {
        setOrderBy("boughtTime", keyword);
    }

    public int getOrderByBoughtTime() {
        return getOrderByKeyword("boughtTime");
    }

}
