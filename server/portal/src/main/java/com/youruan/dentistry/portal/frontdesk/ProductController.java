package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.query.ProductQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontdesk/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    @RequiresAuthentication
    public ResponseEntity<?> list() {
        ProductQuery qo = new ProductQuery();
        qo.setState(Product.PRODUCT_STATE_SALE);
        qo.setOrderBySales(QueryCondition.ORDER_BY_KEYWORD_DESC);
        List<ExtendedProduct> productList = productService.listAll(qo);
        ImmutableMap<Object, Object> map = ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(productList, "id", "type","name", "price", "intro", "iconPath", "description", "sales"))
                .build();
        return ResponseEntity.ok(map);
    }

    @GetMapping("/get")
    @RequiresAuthentication
    public ResponseEntity<?> get(Long id) {
        Product product = productService.get(id);
        ExtendedProduct vo = productService.handleData(product);
        return ResponseEntity.ok(BeanMapUtils.pick(vo, "id", "name", "intro","type","userType","price","totalAppointNum","peopleNum","iconPath","description","state","detailPathList"));
    }

}
