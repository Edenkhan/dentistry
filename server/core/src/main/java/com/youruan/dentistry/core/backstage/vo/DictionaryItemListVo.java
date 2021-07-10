package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryItemListVo extends BasicDomain {
    private String dictionaryName;
    private String name;
    private String mark;
    private Boolean enabled;
}
