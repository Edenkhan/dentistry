package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典
 */
@Setter
@Getter
public class Dictionary extends BasicDomain {
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典标识
     */
    private String mark;
    /**
     * 字典标识【医生】
     */
    public static final String MARK_DOCTOR = "DOCTOR";
}
