
package com.youruan.dentistry.core.backstage.vo;

import com.youruan.dentistry.core.backstage.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExtendedProduct
    extends Product
{

    private final static long serialVersionUID = 1L;
    private List<String> detailPathList;
}
