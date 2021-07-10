package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.backstage.form.RedeemCodeAddForm;
import com.youruan.dentistry.console.backstage.form.RedeemCodeEditForm;
import com.youruan.dentistry.console.backstage.form.RedeemCodeListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 兑换码管理
 */
@RestController
@RequestMapping("/backstage/redeemCode")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;
    public RedeemCodeController(RedeemCodeService redeemCodeService) {
        this.redeemCodeService = redeemCodeService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.redeemCode.list", description = "兑换码-列表")
    public ResponseEntity<?> list(RedeemCodeListForm form) {
        RedeemCodeQuery qo = form.buildQuery();
        Pagination<ExtendedRedeemCode> pagination = redeemCodeService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "lastModifiedDate", "name", "logo"))
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
//        RedeemCode redeemCode = redeemCodeService.create(
//                form.getName(),
//                form.getLogo());
//        return ResponseEntity.ok(ImmutableMap.builder()
//                .put("id", redeemCode.getId())
//                .build());
        return null;
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

}
