package com.youruan.dentistry.console.backstage.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class DictionaryItemListForm extends ListForm<DictionaryItemQuery> {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String dictionaryName;
    private String name;
    private Boolean enabled;

    public DictionaryItemQuery buildQuery() {
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikeDictionaryName(dictionaryName);
        qo.setLikeName(name);
        qo.setEnabled(enabled);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortOrder().equalsIgnoreCase("descend")
                    ? QueryCondition.ORDER_BY_KEYWORD_DESC
                    : QueryCondition.ORDER_BY_KEYWORD_ASC);
        }
        return qo;
    }

}
