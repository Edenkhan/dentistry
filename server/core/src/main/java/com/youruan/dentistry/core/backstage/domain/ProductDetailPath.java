package com.youruan.dentistry.core.backstage.domain;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品详情主图
 */
@Setter
@Getter
public class ProductDetailPath extends BasicDomain {
    /**
     * 产品详情主图路径
     */
    private String detailPath;
    /**
     * 产品id
     */
    private Long productId;
}
