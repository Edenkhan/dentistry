package com.youruan.dentistry.console.base;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.base.service.SetupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setup")
public class SetupController {

    private final SetupService setupService;

    @GetMapping
    public ResponseEntity<?> setup() {
        setupService.setup();
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("message", "初始化成功")
                .build());
    }

    public SetupController(SetupService setupService) {
        this.setupService = setupService;
    }

}
