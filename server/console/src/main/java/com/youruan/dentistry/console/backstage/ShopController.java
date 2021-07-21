package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.ShopAddForm;
import com.youruan.dentistry.console.backstage.form.ShopEditForm;
import com.youruan.dentistry.console.backstage.form.ShopListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.query.ShopQuery;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 门店管理
 */
@RestController
@RequestMapping("/backstage/shop")
public class ShopController {

    private final ShopService shopService;
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.shop.list", description = "门店-列表")
    public ResponseEntity<?> list(ShopListForm form) {
        ShopQuery qo = form.buildQuery();
        Pagination<ExtendedShop> pagination = shopService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "lastModifiedDate", "name","address","phone","totalNum","appointNum","enabled"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "backstage.shop.get", description = "门店-获取")
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        Shop shop = shopService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(shop, "id", "name", "address","phone","totalNum","appointNum","enabled"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.shop.add", description = "门店-添加")
    public ResponseEntity<?> add(ShopAddForm form) {
        Shop shop = shopService.create(
                form.getName(),
                form.getAddress(),
                form.getPhone(),
                form.getEnabled());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", shop.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.shop.edit", description = "门店-修改")
    public ResponseEntity<?> edit(ShopEditForm form) {
        Shop shop = shopService.get(form.getId());
        shopService.update(
                shop,
                form.getName(),
                form.getAddress(),
                form.getPhone(),
                form.getEnabled());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", shop.getId())
                .build());
    }

    /**
     * 添加总预约次数
     */
    @PostMapping("/addTotalNum")
    @RequiresPermission(value = "backstage.shop.addTotalNum", description = "门店-添加总预约次数")
    public ResponseEntity<?> addTotalNum(ShopEditForm form) {
        Shop shop = shopService.get(form.getId());
        shopService.updateTotalNum(shop, form.getFrequency());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", shop.getId())
                .build());
    }

}
