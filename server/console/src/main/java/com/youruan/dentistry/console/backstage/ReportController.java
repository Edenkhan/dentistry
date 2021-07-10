package com.youruan.dentistry.console.backstage;

import com.youruan.dentistry.console.backstage.form.ReportAddForm;
import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/backstage/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile file) {
        String path = reportService.upload(file);
        return ResponseEntity.ok(path);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(ReportAddForm form) {
        Report report = reportService.create(form.getUserId(),form.getPath());
        return ResponseEntity.ok(report);
    }

}
