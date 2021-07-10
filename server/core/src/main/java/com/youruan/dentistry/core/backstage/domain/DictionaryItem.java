package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典详情
 */
@Setter
@Getter
public class DictionaryItem extends BasicDomain {
    /**
     * 字典详情名称
     */
    private String name;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 父字典id
     */
    private Long dictionaryId;
}
