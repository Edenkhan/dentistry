package com.youruan.dentistry.portal.frontdesk.form;

import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.portal.base.form.ListForm;
import lombok.Setter;

@Setter
public class ReportListForm extends ListForm<ReportQuery> {

    private Long appointId;

    public ReportQuery buildQuery() {
        ReportQuery qo = new ReportQuery();
        qo.setPage(getPage());
        qo.setSync(true);
        qo.setAppointId(appointId);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortOrder().equalsIgnoreCase("descend")
                    ? QueryCondition.ORDER_BY_KEYWORD_DESC
                    : QueryCondition.ORDER_BY_KEYWORD_ASC);
        }
        return qo;
    }

}
