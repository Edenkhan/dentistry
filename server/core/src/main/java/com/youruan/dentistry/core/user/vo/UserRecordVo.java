package com.youruan.dentistry.core.user.vo;

import com.youruan.dentistry.core.base.domain.BasicDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRecordVo extends BasicDomain {
    private String realName;
    private Integer gender;
    private String phoneNumber;
    private Integer reportNum;
    private Integer productNum;
    private Integer state;
}
