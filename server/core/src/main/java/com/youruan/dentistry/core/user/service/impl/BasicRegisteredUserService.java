
package com.youruan.dentistry.core.user.service.impl;

import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.storage.DiskFileStorage;
import com.youruan.dentistry.core.base.storage.UploadFile;
import com.youruan.dentistry.core.base.utils.VerifyCodeUtils;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxUserInfo;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.mapper.RegisteredUserMapper;
import com.youruan.dentistry.core.user.query.RegisteredUserQuery;
import com.youruan.dentistry.core.user.service.RegisteredUserService;
import com.youruan.dentistry.core.user.vo.ExtendedRegisteredUser;
import com.youruan.dentistry.core.user.vo.UserRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicRegisteredUserService
    implements RegisteredUserService
{

    private final RegisteredUserMapper registeredUserMapper;
    private final HttpServletResponse response;
    private final DiskFileStorage diskFileStorage;
    private final OrdersService ordersService;

    public BasicRegisteredUserService(RegisteredUserMapper registeredUserMapper, HttpServletResponse response, DiskFileStorage diskFileStorage, OrdersService ordersService) {
        this.registeredUserMapper = registeredUserMapper;
        this.response = response;
        this.diskFileStorage = diskFileStorage;
        this.ordersService = ordersService;
    }

    @Override
    public RegisteredUser get(Long id) {
        return registeredUserMapper.get(id);
    }

    protected void update(RegisteredUser registeredUser) {
        int affected = registeredUserMapper.update(registeredUser);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        registeredUser.setVersion((registeredUser.getVersion()+ 1));
    }

    protected RegisteredUser add(RegisteredUser registeredUser) {
        registeredUser.setCreatedDate(new Date());
        registeredUserMapper.add(registeredUser);
        return registeredUser;
    }

    @Override
    public List<ExtendedRegisteredUser> list(RegisteredUserQuery qo) {
        return registeredUserMapper.query(qo);
    }

    @Override
    public ExtendedRegisteredUser queryOne(RegisteredUserQuery qo) {
        qo.setPageSize(1);
        List<ExtendedRegisteredUser> data = registeredUserMapper.query(qo);
        return (((data == null)||data.isEmpty())?null:data.get(0));
    }

    @Override
    public Pagination<ExtendedRegisteredUser> query(RegisteredUserQuery qo) {
        int rows = registeredUserMapper.count(qo);
        List<ExtendedRegisteredUser> data = ((rows == 0)?new ArrayList<>():registeredUserMapper.query(qo));
        return new Pagination<>(rows, data);
    }

    @Override
    public int count(RegisteredUserQuery qo) {
        return registeredUserMapper.count(qo);
    }

    @Override
    @Transactional
    public RegisteredUser create(WxUserInfo wxUserInfo) {
        RegisteredUser user = new RegisteredUser();
        user.setOpenid(wxUserInfo.getOpenid());
        user.setNickname(wxUserInfo.getNickname());
        user.setAvatar(wxUserInfo.getHeadimgurl());
        return add(user);
    }

    @Override
    public void update(RegisteredUser user, String phoneNumber, Boolean locked) {
        this.checkUpdate(user,phoneNumber,locked);
        user.setPhoneNumber(phoneNumber);
        user.setLocked(locked);
        update(user);
    }

    @Override
    public void update(RegisteredUser user, String realName) {
        Assert.notNull(user,"必须提供用户");
        Assert.notNull(realName,"必须提供姓名");
        user.setRealName(realName);
        this.update(user);
    }

    @Override
    public void update(RegisteredUser user, Integer gender) {
        Assert.notNull(user,"必须提供用户");
        Assert.notNull(gender,"必须提供性别");
        user.setGender(gender);
        this.update(user);
    }

    private void checkUpdate(RegisteredUser user, String phoneNumber, Boolean locked) {
        Assert.notNull(user, "必须提供用户");
        Assert.notNull(phoneNumber, "必须提供手机号");
        Assert.notNull(locked, "必须提供用户锁定状态");
        RegisteredUserQuery qo = new RegisteredUserQuery();
        qo.setPhoneNumber(phoneNumber);
        int count = this.count(qo);
        Assert.isTrue(phoneNumber.equals(user.getPhoneNumber()) || count == 0,"手机号已被注册");
    }

    @Override
    public String getVerify() {
        try{
            int width = 200;
            int height = 69;
            //生成对应宽高的初始图片
            BufferedImage verifyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            String randomText = VerifyCodeUtils.drawRandomText(width, height, verifyImage);
            //必须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            ServletOutputStream os = response.getOutputStream();
            //输出图片
            ImageIO.write(verifyImage,"png",os);
            return randomText;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String upload(MultipartFile file, String directory) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setOriginalFilename(file.getOriginalFilename());
            uploadFile.setInputStream(file.getInputStream());
            return diskFileStorage.store(uploadFile,directory);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateAvatar(RegisteredUser user, String avatar) {
        Assert.notNull(user,"必须提供用户");
        user.setAvatar(avatar);
        this.update(user);
    }

    @Override
    public List<UserRecordVo> handleData(List<ExtendedRegisteredUser> userList) {
        OrdersQuery qo = new OrdersQuery();
        return userList.stream().map(item -> {
            UserRecordVo vo = new UserRecordVo();
            BeanUtils.copyProperties(item,vo);
            qo.setUserId(item.getId());
            qo.setPayStatus(Orders.PAY_STATUS_PAID);
            vo.setProductCounts(ordersService.count(qo));
            return vo;
        }).collect(Collectors.toList());
    }


}
