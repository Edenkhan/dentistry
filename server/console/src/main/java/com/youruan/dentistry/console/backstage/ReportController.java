package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.ReportAddForm;
import com.youruan.dentistry.console.backstage.form.ReportEditForm;
import com.youruan.dentistry.console.backstage.form.ReportListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ReportRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
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
    @RequiresPermission(value = "backstage.report.upload", description = "报告-上传")
    public ResponseEntity<?> upload(MultipartFile file) {
        String path = reportService.upload(file,"report");
        return ResponseEntity.ok(path);
    }

    @PostMapping("/add")
    @RequiresPermission(value = "backstage.report.add", description = "报告-添加")
    public ResponseEntity<?> add(ReportAddForm form) {
        Appointment appointment = appointmentService.get(form.getAppointId());
        Assert.notNull(appointment,"必须提供预约信息");
        Product product = productService.get(appointment.getProductId());
        Report report = reportService.create(product.getPeopleNum(),
                form.getUserId(),
                form.getAppointId(),
                product.getId(),
                form.getPathList());
        return ResponseEntity.ok(report);
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.report.list", description = "报告-列表")
    public ResponseEntity<?> list(ReportListForm form) {
        ReportQuery qo = form.buildQuery();
        Pagination<ReportRecordVo> pagination = reportService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data",pagination.getData())
                .put("rows",pagination.getRows())
                .build());
    }

    @PostMapping("/sync")
    @RequiresPermission(value = "backstage.report.sync", description = "报告-同步")
    public ResponseEntity<?> sync(ReportEditForm form) {
        Report report = reportService.get(form.getId());
        reportService.sync(report);
        return ResponseEntity.ok().build();
    }

    /**
     * 重新上传
     */
    @PostMapping("/reset")
    @RequiresPermission(value = "backstage.report.reset", description = "报告-重新上传")
    public ResponseEntity<?> reset(ReportEditForm form) {
        Report report = reportService.get(form.getId());
        reportService.reset(report,form.getPathList());
        return ResponseEntity.ok().build();
    }

    /**
     * 获取预约记录
     */
    @GetMapping("/getAppoint")
    @RequiresPermission(value = "backstage.report.getAppoint", description = "报告-获取预约")
    public ResponseEntity<?> getAppoint(Long id) {
        Report report = reportService.get(id);
        AppointRecordVo vo = reportService.getAppoint(report);
        return ResponseEntity.ok(vo);
    }

}
