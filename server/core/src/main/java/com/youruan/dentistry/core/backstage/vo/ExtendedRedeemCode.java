
package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.domain.Shop;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendedRedeemCode
    extends RedeemCode
{

    private final static long serialVersionUID = 1L;

    /**
     * 产品
     */
    private Product product;
    /**
     * 门店
     */
    private Shop shop;
}
