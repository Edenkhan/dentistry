package com.youruan.dentistry.console.message;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.message.form.SmsVerificationListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.message.query.SmsVerificationQuery;
import com.youruan.dentistry.core.message.service.SmsVerificationService;
import com.youruan.dentistry.core.message.vo.ExtendedSmsVerification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/smsVerification")
public class SmsVerificationController {

    private final SmsVerificationService smsVerificationService;

    public SmsVerificationController(SmsVerificationService smsVerificationService) {
        this.smsVerificationService = smsVerificationService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "message.smsVerification.list", description = "验证短信-列表")
    public ResponseEntity<?> list(SmsVerificationListForm form) {
        SmsVerificationQuery qo = form.buildQuery();
        Pagination<ExtendedSmsVerification> pagination = smsVerificationService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "phoneNumber", "code", "requestIp", "retryCount", "expirationDate", "state", "type"))
                .put("rows", pagination.getRows())
                .build());
    }

}
