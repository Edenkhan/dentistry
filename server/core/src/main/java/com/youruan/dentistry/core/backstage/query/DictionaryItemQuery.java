
package com.youruan.dentistry.core.backstage.query;

import com.youruan.dentistry.core.base.query.QueryCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DictionaryItemQuery
    extends QueryCondition
{

    protected Long[] ids;
    protected Date startCreatedDate;
    protected Date endCreatedDate;
    protected String dictionaryName;
    protected String likeDictionaryName;
    protected String name;
    protected String likeName;
    protected Boolean enabled;
    protected Long dictionaryId;
    private String mark;

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

}
