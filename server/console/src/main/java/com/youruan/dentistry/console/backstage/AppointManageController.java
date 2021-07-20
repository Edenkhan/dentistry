package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.AppointManageEditForm;
import com.youruan.dentistry.console.backstage.form.AppointManageListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约管理
 */
@RestController
@RequestMapping("/backstage/appointManage")
public class AppointManageController {

    private final AppointManageService appointManageService;

    public AppointManageController(AppointManageService appointManageService) {
        this.appointManageService = appointManageService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generate() {
        appointManageService.generate();
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/checkDataSource")
    public ResponseEntity<?> checkDataSource(Long shopId) {
        appointManageService.checkDataSource(shopId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.appointManage.list", description = "预约管理-列表")
    public ResponseEntity<?> list(AppointManageListForm form) {
        AppointManageQuery qo = form.buildQuery();
        Pagination<ExtendedAppointManage> pagination = appointManageService.query(qo);
        List<Object> list = appointManageService.handleData(pagination.getData());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", list)
                .put("rows", 2)
                .build());
    }

    @GetMapping("/getOneByShopId")
    @RequiresPermission(value = "backstage.appointManage.getOneByShopId", description = "预约管理-根据门店id获取")
    public ResponseEntity<?> getOneByShopId(@RequestParam("shopId") Long shopId) {
        AppointManage appointManage = appointManageService.getOneByShopId(shopId);
        return ResponseEntity.ok(BeanMapUtils.pick(appointManage, "id", "topLimit"));
    }

//    @GetMapping("/get")
//    @RequiresPermission(value = "backstage.appointManage.get", description = "预约管理-获取")
//    public ResponseEntity<?> list(@RequestParam("id") Long id) {
//        AppointManage appointManage = appointManageService.get(id);
//        return ResponseEntity.ok(BeanMapUtils.pick(appointManage, "id", "name", "mark"));
//    }

    //    @PostMapping("/add")
//    @RequiresPermission(value = "backstage.appointManage.add", description = "预约管理-添加")
//    public ResponseEntity<?> add() {
//        AppointManage appointManage = appointManageService.create(
//                form.getName(),
//                form.getMark());
//        return ResponseEntity.ok(ImmutableMap.builder()
//                .put("id", appointManage.getId())
//                .build());
//    }
//
    @PostMapping("/edit")
    @RequiresPermission(value = "backstage.appointManage.edit", description = "预约管理-修改")
    public ResponseEntity<?> edit(AppointManageEditForm form) {
        AppointManage appointManage = appointManageService.get(form.getId());
        appointManageService.update(
                appointManage,
                form.getEnabled());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", appointManage.getId())
                .build());
    }

    @PostMapping("/updateTopLimit")
    @RequiresPermission(value = "backstage.appointManage.updateTopLimit", description = "预约管理-修改门店预约上限")
    public ResponseEntity<?> editAllTopLimit(AppointManageEditForm form) {
        AppointManage appointManage = appointManageService.get(form.getId());
        appointManageService.updateTopLimit(
                appointManage,
                form.getAmTopLimit(),
                form.getPmTopLimit());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", appointManage.getId())
                .build());
    }

}
