package com.youruan.dentistry.portal.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/session")
public class SessionController {

    @GetMapping("/keep")
    public ResponseEntity<?> keep() {
        return ResponseEntity.ok().build();
    }

}
