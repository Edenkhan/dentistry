package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 兑换码
 */
@Setter
@Getter
public class RedeemCode extends BasicDomain {
    /**
     * 是否已使用
     */
    private Boolean used;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 门店id
     */
    private Long shopId;
}
