package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.utils.DateUtil;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/frontdesk/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final OrdersService ordersService;
    private final AppointManageService appointManageService;

    public AppointmentController(AppointmentService appointmentService, OrdersService ordersService, AppointManageService appointManageService) {
        this.appointmentService = appointmentService;
        this.ordersService = ordersService;
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
        qo.setState(Appointment.STATE_APPOINTED);
        ExtendedAppointment appointment = appointmentService.queryOne(qo);
        appointmentService.update(appointment,form.getTimePeriod(),form.getAppointDate());
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/getByUser")
    @RequiresAuthentication
    public ResponseEntity<?> getByUser(RegisteredUser user, Long orderId) {
        List<ExtendedAppointment> appointmentList = appointmentService.getByUser(user.getId(), orderId);
        ImmutableMap<Object, Object> map = ImmutableMap.builder().put("data", appointmentList).build();
        return ResponseEntity.ok(map);
    }

    /**
     * 获取门店 可预约时间
     */
    @GetMapping("/getAppointDate")
    @RequiresAuthentication
    public ResponseEntity<?> getAppointDate(Long orderId) {
        Orders orders = ordersService.get(orderId);
        AppointManageQuery qo = new AppointManageQuery();
        qo.setStartAppointDate(DateUtil.getStartTime(new Date()));
        qo.setShopId(orders.getShopId());
        List<ExtendedAppointManage> appointManageList = appointManageService.listAll(qo);
        List<AppointDateVo> voList = appointmentService.handleData(appointManageList);
        ImmutableMap<Object, Object> data = ImmutableMap.builder().put("data", voList).build();
        return ResponseEntity.ok(data);
    }
}
