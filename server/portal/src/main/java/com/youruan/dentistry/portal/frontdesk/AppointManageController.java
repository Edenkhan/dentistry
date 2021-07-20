package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.frontdesk.form.AppointManageForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontdesk/appointManage")
public class AppointManageController {

    private final AppointManageService appointManageService;

    public AppointManageController(AppointManageService appointManageService) {
        this.appointManageService = appointManageService;
    }

    /**
     * 查看当前时段是否爆满
     */
    @GetMapping("/checkFull")
    @RequiresAuthentication
    public ResponseEntity<?> checkFull(AppointManageForm form) {
        Boolean isFull = appointManageService.checkFull(form.getOrderId(),
                form.getAppointDate(),
                form.getTimePeriod());
        return ResponseEntity.ok(isFull);
    }


    /**
     * 获取当前日期已启用的时段
     */
    @GetMapping("/getValidPeriod")
    @RequiresAuthentication
    public ResponseEntity<?> getValidPeriod(AppointManageForm form) {
        List<Integer> timePeriodList = appointManageService.getValidPeriod(form.getOrderId(),
                form.getAppointDate());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("periodList",timePeriodList).build());
    }
}
