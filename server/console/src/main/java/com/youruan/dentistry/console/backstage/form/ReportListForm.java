package com.youruan.dentistry.console.backstage.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.base.query.QueryCondition;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class ReportListForm extends ListForm<ReportQuery> {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private Long userId;
    private String productName;
    private Integer productType;
    private Integer userType;

    public ReportQuery buildQuery() {
        ReportQuery qo = new ReportQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setUserId(userId);
        qo.setLikeProductName(productName);
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
