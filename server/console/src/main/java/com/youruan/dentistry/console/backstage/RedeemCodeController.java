package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.RedeemCodeAddForm;
import com.youruan.dentistry.console.backstage.form.RedeemCodeEditForm;
import com.youruan.dentistry.console.backstage.form.RedeemCodeListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 兑换码管理
 */
@RestController
@RequestMapping("/backstage/redeemCode")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;
    private final ProductService productService;
    private final ShopService shopService;

    public RedeemCodeController(RedeemCodeService redeemCodeService, ProductService productService, ShopService shopService) {
        this.redeemCodeService = redeemCodeService;
        this.productService = productService;
        this.shopService = shopService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.redeemCode.list", description = "兑换码-列表")
    public ResponseEntity<?> list(RedeemCodeListForm form) {
        RedeemCodeQuery qo = form.buildQuery();
        Pagination<ExtendedRedeemCode> pagination = redeemCodeService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", pagination.getData())
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "backstage.redeemCode.get", description = "兑换码-获取")
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        RedeemCode redeemCode = redeemCodeService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(redeemCode, "id", "name", "logo"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.redeemCode.add", description = "兑换码-添加")
    public ResponseEntity<?> add(RedeemCodeAddForm form) {
        redeemCodeService.create(form.getProductId(),
                form.getShopId(),
                form.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.redeemCode.edit", description = "兑换码-修改")
    public ResponseEntity<?> edit(RedeemCodeEditForm form) {
//        RedeemCode redeemCode = redeemCodeService.get(form.getId());
//        redeemCodeService.update(
//                redeemCode,
//                form.getName(),
//                form.getLogo());
//        return ResponseEntity.ok(ImmutableMap.builder()
//                .put("id", redeemCode.getId())
//                .build());
        return null;
    }

    @GetMapping("/product")
    @RequiresPermission(value = "backstage.redeemCode.product", description = "兑换码-查询所有产品")
    public ResponseEntity<?> product() {
        List<ExtendedProduct> productList = productService.listAll();
        return ResponseEntity.ok(ImmutableMap.builder().put("data",productList).build());
    }

    @GetMapping("/shop")
    @RequiresPermission(value = "backstage.redeemCode.shop", description = "兑换码-查询所有门店")
    public ResponseEntity<?> shop() {
        List<ExtendedShop> shopList = shopService.listAll();
        return ResponseEntity.ok(ImmutableMap.builder().put("data",shopList).build());
    }

//    @GetMapping("/doctor")
//    @RequiresPermission(value = "backstage.redeemCode.doctor", description = "兑换码-查询所有医生")
//    public ResponseEntity<?> doctor() {
//        DictionaryItemQuery qo = new DictionaryItemQuery();
//        qo.setMark(Dictionary.MARK_DOCTOR);
//        List<ExtendedDictionaryItem> doctorList = dictionaryItemService.listAll(qo);
//        return ResponseEntity.ok(ImmutableMap.builder().put("data",doctorList).build());
//    }

}
