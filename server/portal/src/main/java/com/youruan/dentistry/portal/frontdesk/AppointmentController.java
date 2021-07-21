package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import com.youruan.dentistry.core.frontdesk.vo.AppointDateVo;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedAppointment;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.frontdesk.form.AppointmentAddForm;
import com.youruan.dentistry.portal.frontdesk.form.AppointmentEditForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontdesk/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointManageService appointManageService;

    public AppointmentController(AppointmentService appointmentService, AppointManageService appointManageService) {
        this.appointmentService = appointmentService;
        this.appointManageService = appointManageService;
    }

    @PostMapping("/add")
    @RequiresAuthentication
    public ResponseEntity<?> add(RegisteredUser user, AppointmentAddForm form) {
        Appointment appointment = appointmentService.create(form.getAppointDate(),
                form.getTimePeriod(),
                form.getOrderId(),
                user.getId());
        return ResponseEntity.ok(appointment);
    }

    @PostMapping("/edit")
    @RequiresAuthentication
    public ResponseEntity<?> edit(AppointmentEditForm form) {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setOrderId(form.getOrderId());
        qo.setAppointState(Appointment.APPOINT_STATE_APPOINTED);
        ExtendedAppointment appointment = appointmentService.queryOne(qo);
        appointmentService.update(appointment,form.getTimePeriod(),form.getAppointDate());
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/getByUser")
    @RequiresAuthentication
    public ResponseEntity<?> getByUser(RegisteredUser user, Long orderId) {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setUserId(user.getId());
        qo.setOrderId(orderId);
        Pagination<AppointRecordVo> pagination = appointmentService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", pagination.getData())
                .build());
    }

    /**
     * 获取门店 可预约时间
     */
    @GetMapping("/getAppointDate")
    @RequiresAuthentication
    public ResponseEntity<?> getAppointDate(Long orderId) {
        List<ExtendedAppointManage> extendedAppointManageList = appointManageService.getAppointDateList(orderId);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(extendedAppointManageList,"appointDate"))
                .build());
    }

    /**
     * 获取正在预约中的预约信息
     */
    @GetMapping("/getAppointing")
    @RequiresAuthentication
    public ResponseEntity<?> getAppointing(Long orderId) {
        ExtendedAppointment extendedAppointment = appointmentService.getAppointing(orderId);
        AppointDateVo vo = appointmentService.handleData(extendedAppointment);
        return ResponseEntity.ok(vo);
    }


}
