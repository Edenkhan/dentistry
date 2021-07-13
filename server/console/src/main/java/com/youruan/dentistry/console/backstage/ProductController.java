package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.ProductAddForm;
import com.youruan.dentistry.console.backstage.form.ProductEditForm;
import com.youruan.dentistry.console.backstage.form.ProductListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.query.ProductQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/backstage/product")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.product.list", description = "产品-列表")
    public ResponseEntity<?> list(ProductListForm form) {
        ProductQuery qo = form.buildQuery();
        Pagination<ExtendedProduct> pagination = productService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "lastModifiedDate", "name", "type","userType", "price", "totalAppointNum","peopleNum","state"))
                .put("rows", pagination.getRows())
                .build());
    }

    @GetMapping("/get")
    @RequiresPermission(value = "backstage.product.get", description = "产品-获取")
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        Product product = productService.get(id);
        return ResponseEntity.ok(BeanMapUtils.pick(product, "id", "name", "intro","type","userType","price","totalAppointNum","peopleNum","iconPath","description","state"));
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.product.add", description = "产品-添加")
    public ResponseEntity<?> add(ProductAddForm form) {
        Product product = productService.create(
                form.getName(),
                form.getIntro(),
                form.getType(),
                form.getUserType(),
                form.getPrice(),
                form.getTotalAppointNum(),
                form.getPeopleNum(),
                form.getIconPath(),
                form.getDetailPathList(),
                form.getDescription(),
                form.getState());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", product.getId())
                .build());
    }

    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.product.edit", description = "产品-修改")
    public ResponseEntity<?> edit(ProductEditForm form) {
        Product product = productService.get(form.getId());
        productService.update(
                product,
                form.getName(),
                form.getIntro(),
                form.getType(),
                form.getUserType(),
                form.getPrice(),
                form.getTotalAppointNum(),
                form.getPeopleNum(),
                form.getIconPath(),
                form.getDetailPathList(),
                form.getDescription(),
                form.getState());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", product.getId())
                .build());
    }

    @PostMapping("/uploadIcon")
    @RequiresPermission(value = "backstage.activity.uploadIcon",description = "产品列表主图-上传")
    public ResponseEntity<?> uploadIcon(MultipartFile file) {
        List<String> iconPath = productService.upload("producticon",file);
        return ResponseEntity.ok(ImmutableMap.builder().put("iconPath",iconPath.get(0)).build());
    }

    @PostMapping("/uploadDetail")
    @RequiresPermission(value = "backstage.activity.uploadDetail",description = "产品详情主图-上传")
    public ResponseEntity<?> uploadDetail(MultipartFile[] files) {
        List<String> detailPathList = productService.upload("productdetail",files);
        return ResponseEntity.ok(ImmutableMap.builder().put("detailPathList",detailPathList).build());
    }

}
