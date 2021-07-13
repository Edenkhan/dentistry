
package com.youruan.dentistry.core.user.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxUserInfo;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.query.RegisteredUserQuery;
import com.youruan.dentistry.core.user.vo.ExtendedRegisteredUser;
import com.youruan.dentistry.core.user.vo.UserRecordVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RegisteredUserService {


    public RegisteredUser get(Long id);

    public ExtendedRegisteredUser queryOne(RegisteredUserQuery qo);

    public List<ExtendedRegisteredUser> list(RegisteredUserQuery qo);

    public Pagination<ExtendedRegisteredUser> query(RegisteredUserQuery qo);

    public int count(RegisteredUserQuery qo);

    RegisteredUser create(WxUserInfo wxUserInfo);

    void update(RegisteredUser user, String phoneNumber, Boolean locked);
    void update(RegisteredUser user, String realName);
    void update(RegisteredUser user, Integer gender);

    /**
     * 获取验证码图片
     */
    String getVerify();

    /**
     * 上传头像
     */
    String upload(MultipartFile file, String directory);

    /**
     * 修改头像
     */
    void updateAvatar(RegisteredUser user, String avatar);

    /**
     * 处理返回结果 追加
     */
    List<UserRecordVo> handleData(List<ExtendedRegisteredUser> userList);

    /**
     * 修改手机号
     */
    void changePhoneNumber(RegisteredUser user, String phoneNumber);
}
