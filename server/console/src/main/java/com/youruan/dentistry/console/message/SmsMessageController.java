package com.youruan.dentistry.console.message;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.console.message.form.SmsMessageListForm;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.message.query.SmsMessageQuery;
import com.youruan.dentistry.core.message.service.SmsMessageService;
import com.youruan.dentistry.core.message.vo.ExtendedSmsMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/sms")
public class SmsMessageController {

    private final SmsMessageService smsMessageService;

    public SmsMessageController(SmsMessageService smsMessageService) {
        this.smsMessageService = smsMessageService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "message.sms.list", description = "短信消息-列表")
    public ResponseEntity<?> list(SmsMessageListForm form) {
        SmsMessageQuery qo = form.buildQuery();
        Pagination<ExtendedSmsMessage> pagination = smsMessageService.query(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", BeanMapUtils.pick(pagination.getData(),
                        "id", "createdDate", "phoneNumber", "templateId", "content", "closeDate", "note", "state"))
                .put("rows", pagination.getRows())
                .build());
    }

}
