package com.youruan.dentistry.console.platform.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ChangePasswordForm {

    @NotNull(message = "原密码不能为空")
    private String originalPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;

    @NotNull(message = "确认新密码不能为空")
    private String confirmNewPassword;

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

}
