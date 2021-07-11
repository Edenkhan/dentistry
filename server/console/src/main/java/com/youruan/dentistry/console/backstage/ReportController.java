package com.youruan.dentistry.console.backstage;

import com.youruan.dentistry.console.backstage.form.ReportAddForm;
import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/backstage/report")
public class ReportController {

    private final ReportService reportService;
    private final AppointmentService appointmentService;
    private final ProductService productService;

    public ReportController(ReportService reportService, AppointmentService appointmentService, ProductService productService) {
        this.reportService = reportService;
        this.appointmentService = appointmentService;
        this.productService = productService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile file) {
        String path = reportService.upload(file,"report");
        return ResponseEntity.ok(path);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(ReportAddForm form) {
        Appointment appointment = appointmentService.get(form.getAppointId());
        Assert.notNull(appointment,"必须提供预约信息");
        Product product = productService.get(appointment.getProductId());
        Report report = reportService.create(product.getPeopleNum(),
                form.getUserId(),
                form.getAppointId(),
                form.getPathList());
        return ResponseEntity.ok(report);
    }

}
