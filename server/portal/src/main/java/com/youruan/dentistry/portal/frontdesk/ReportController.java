package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.backstage.vo.ReportRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.frontdesk.form.ReportListForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontdesk/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/list")
    @RequiresAuthentication
    public ResponseEntity<?> list(RegisteredUser user, ReportListForm form) {
        ReportQuery qo = form.buildQuery();
        qo.setUserId(user.getId());
        Pagination<ReportRecordVo> pagination = reportService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data",pagination.getData())
                .build());
    }

    @GetMapping("/get")
    @RequiresAuthentication
    public ResponseEntity<?> get(Long id) {
        Report report = reportService.get(id);
        return ResponseEntity.ok(report);
    }
}
