package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.AppointRecordEditForm;
import com.youruan.dentistry.console.backstage.form.AppointRecordListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约记录
 */
@RestController
@RequestMapping("/backstage/appointRecord")
public class AppointRecordController {

    private final AppointmentService appointmentService;

    public AppointRecordController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.appointRecord.list", description = "预约记录-列表")
    public ResponseEntity<?> list(AppointRecordListForm form) {
        AppointmentQuery qo = form.buildQuery();
        Pagination<AppointRecordVo> pagination = appointmentService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", pagination.getData())
                .put("rows", pagination.getRows())
                .build());
    }

    @PostMapping("/appointCompleted")
    @RequiresPermission(value = "backstage.appointRecord.appointCompleted", description = "预约完成")
    public ResponseEntity<?> appointCompleted(AppointRecordEditForm form) {
        Appointment appointment = appointmentService.get(form.getId());
        appointmentService.appointCompleted(appointment);
        return ResponseEntity.ok().build();
    }
    
}
