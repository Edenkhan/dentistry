package com.youruan.dentistry.portal.frontdesk;

import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.frontdesk.form.RedeemCodeEditForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontdesk/redeemCode")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    public RedeemCodeController(RedeemCodeService redeemCodeService) {
        this.redeemCodeService = redeemCodeService;
    }

    @GetMapping("/getProductType")
    @RequiresAuthentication
    public ResponseEntity<?> getDoctor(String code) {
        RedeemCodeQuery qo = new RedeemCodeQuery();
        qo.setCode(code);
        ExtendedRedeemCode vo = redeemCodeService.queryOne(qo);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/bindUser")
    @RequiresAuthentication
    public ResponseEntity<?> bindUser(RegisteredUser user, RedeemCodeEditForm form) {
        RedeemCode redeemCode = redeemCodeService.getByCode(form.getCode());
        redeemCodeService.bindUser(redeemCode,user.getId(),form.getDicItemName());
        return ResponseEntity.ok().build();
    }
}
