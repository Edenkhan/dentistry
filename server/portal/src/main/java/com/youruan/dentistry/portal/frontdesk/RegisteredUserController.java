package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.message.domain.SmsVerification;
import com.youruan.dentistry.core.message.service.SmsVerificationService;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.service.RegisteredUserService;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.base.utils.SessionUtils;
import com.youruan.dentistry.portal.frontdesk.form.RegisteredUserEditForm;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/registeredUser")
public class RegisteredUserController {

    private final RegisteredUserService userService;
    private final SmsVerificationService smsVerificationService;
    private final HttpServletRequest request;

    public RegisteredUserController(RegisteredUserService userService, SmsVerificationService smsVerificationService, HttpServletRequest request) {
        this.userService = userService;
        this.smsVerificationService = smsVerificationService;
        this.request = request;
    }

    @GetMapping("/get")
    @RequiresAuthentication
    public ResponseEntity<?> get(RegisteredUser user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/edit")
    @RequiresAuthentication
    public ResponseEntity<?> edit(RegisteredUser user, RegisteredUserEditForm form) {
        userService.update(user, form.getRealName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/changeGender")
    @RequiresAuthentication
    public ResponseEntity<?> changeGender(RegisteredUser user, RegisteredUserEditForm form) {
        userService.update(user, form.getGender());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getVerify")
    public ResponseEntity<?> getVerify() {
        String verifyCode = userService.getVerify();
        SessionUtils.setCode(verifyCode);
        System.out.println("**********"+verifyCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkVerify")
    public ResponseEntity<?> checkVerify(String verifyCode) {
        boolean passed = false;
        if(SessionUtils.hasCode()) {
            passed = SessionUtils.getCode().equalsIgnoreCase(verifyCode);
        }
        return ResponseEntity.ok(ImmutableMap.builder().put("passed",passed).build());
    }

    @PostMapping("/sendVerifyCode")
    public ResponseEntity<?> sendVerifyCode(String phoneNumber) {
        SmsVerification smsVerification = smsVerificationService.sendLogin(phoneNumber, request.getRemoteAddr());
        request.getSession().setAttribute(phoneNumber,smsVerification.getCode());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(RegisteredUser user, String phoneNumber, String verifyCode) {
        String sessionCode = (String) request.getSession().getAttribute(phoneNumber);
        Assert.isTrue(sessionCode!=null && sessionCode.equals(verifyCode),"手机验证码错误");
        userService.update(user,phoneNumber,false);
        ImmutableMap<Object, Object> map = ImmutableMap.builder().put("id", user.getId()).build();
        return ResponseEntity.ok(map);
    }

    @PostMapping("/uploadAvatar")
    @RequiresAuthentication
    public ResponseEntity<?> uploadAvatar(RegisteredUser user, MultipartFile file) {
        String avatar = userService.upload(file, "avatar");
        userService.updateAvatar(user,avatar);
        return ResponseEntity.ok(avatar);
    }

    @PostMapping("/changePhoneNumber")
    @RequiresAuthentication
    public ResponseEntity<?> changePhoneNumber(RegisteredUser user, String phoneNumber) {
        userService.changePhoneNumber(user,phoneNumber);
        return ResponseEntity.ok(phoneNumber);
    }

    @PostMapping("/checkOldPhone")
    @RequiresAuthentication
    public ResponseEntity<?> checkOldPhone(RegisteredUser user, String verifyCode) {
        String phoneCode = (String) request.getSession().getAttribute(user.getPhoneNumber());
        Assert.isTrue(phoneCode!=null && phoneCode.equals(verifyCode),"手机验证码错误");
        return ResponseEntity.ok().build();
    }
}
