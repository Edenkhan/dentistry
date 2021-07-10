package com.youruan.dentistry.portal.frontdesk;

import com.youruan.dentistry.core.frontdesk.service.AccessService;
import com.youruan.dentistry.portal.frontdesk.form.AccessParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    /**
     * 对接微信公众号
     */
    @GetMapping
    public String access(AccessParam param){
        return accessService.access(param.getSignature(),
                param.getTimestamp(),
                param.getNonce(),
                param.getEchostr());
    }

}
