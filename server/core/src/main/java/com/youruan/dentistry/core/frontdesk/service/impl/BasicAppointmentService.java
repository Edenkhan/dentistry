package com.youruan.dentistry.core.frontdesk.service.impl;


import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.DateUtil;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.mapper.AppointmentMapper;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import com.youruan.dentistry.core.frontdesk.vo.AppointDateVo;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedAppointment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicAppointmentService implements AppointmentService {
    private final AppointmentMapper appointmentMapper;
    private final OrdersService ordersService;
    private final AppointManageService appointManageService;
    private final ShopService shopService;

    public BasicAppointmentService(AppointmentMapper appointmentMapper, OrdersService ordersService, AppointManageService appointManageService, ShopService shopService) {
        this.appointmentMapper = appointmentMapper;
        this.ordersService = ordersService;
        this.appointManageService = appointManageService;
        this.shopService = shopService;
    }

    @Override
    public Appointment get(Long id) {
        return appointmentMapper.get(id);
    }

    protected void update(Appointment appointment) {
        int affected = appointmentMapper.update(appointment);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        appointment.setVersion((appointment.getVersion() + 1));
    }

    protected Appointment add(Appointment appointment) {
        appointment.setCreatedDate(new Date());
        appointmentMapper.add(appointment);
        return appointment;
    }

    @Override
    public List<ExtendedAppointment> listAll(AppointmentQuery qo) {
        return appointmentMapper.query(qo);
    }

    @Override
    public ExtendedAppointment queryOne(AppointmentQuery qo) {
        qo.setPageSize(1);
        List<ExtendedAppointment> datas = appointmentMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedAppointment> query(AppointmentQuery qo) {
        int rows = appointmentMapper.count(qo);
        List<ExtendedAppointment> datas = ((rows == 0) ? new ArrayList<>() : appointmentMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(AppointmentQuery qo) {
        return appointmentMapper.count(qo);
    }

    @Override
    @Transactional
    public Appointment create(Date appointDate, Integer timePeriod, Long orderId, Long userId) {
        this.checkAdd(appointDate, timePeriod, orderId, userId);
        Appointment appointment = new Appointment();
        Orders orders = ordersService.get(orderId);
        this.assign(appointment,appointDate,timePeriod, orderId,userId,orders.getProductId(),orders.getShopId());
        appointment = this.add(appointment);
        Assert.notNull(appointment,"预约失败");
        // 更新订单预约次数
        ordersService.updateAppointNum(orders);
        // 更新预约管理 预约次数
        ExtendedAppointManage extendedAppointManage = getExtendedAppointManage(appointDate, timePeriod);
        AppointManage appointManage = new AppointManage();
        BeanUtils.copyProperties(extendedAppointManage,appointManage);
        appointManageService.updateAppointNum(appointManage);
        // 更新门店预约次数
        Shop shop = shopService.get(orders.getShopId());
        shopService.updateAppointNum(shop);
        return appointment;
    }

    private void checkAdd(Date appointDate, Integer timePeriod, Long orderId, Long userId) {

        Assert.notNull(appointDate,"必须提供预约日期");
        Assert.notNull(timePeriod,"必须提供预约时间段");
        Assert.notNull(orderId,"必须提供订单id");
        Assert.notNull(userId,"必须提供用户id");
        this.checkDate(timePeriod,DateUtil.getStartTime(appointDate));
        this.checkUserAppoint(userId);
        this.checkOrderAppointNum(orderId);
        this.checkAppointDateAndStatus(appointDate, timePeriod);
        Orders orders = ordersService.get(orderId);
        this.checkShopValid(orders.getShopId());
    }

    /**
     * 用户是否已经有过预约
     */
    private void checkUserAppoint(Long userId) {
        AppointmentQuery aq = new AppointmentQuery();
        aq.setUserId(userId);
        aq.setState(Appointment.STATE_APPOINTED);
        int count = this.count(aq);
        Assert.isTrue(count == 0,"该用户已经有过预约");
    }

    /**
     * 当前订单是否还有预约次数
     */
    private void checkOrderAppointNum(Long orderId) {
        Orders orders = ordersService.get(orderId);
        Assert.notNull(orders,"必须提供订单");
        Assert.isTrue(orders.getAppointNum()<orders.getTotalNum(),"订单预约次数已用完");
    }

    /**
     * 门店总预约数量是否已满
     */
    private void checkShopValid(Long shopId) {
        Shop shop = shopService.get(shopId);
        Assert.notNull(shop,"必须提供门店");
        List<ExtendedAppointManage> appointManageList = appointManageService.listAll();
        Integer appointSum = appointManageList.stream().map(AppointManage::getAppointNum).reduce(0, Integer::sum);
        System.out.println("总共已预约次数："+appointSum);
        Assert.isTrue(appointSum < shop.getValidNum(),"门店总预约次数已满");
    }

    /**
     * 该预约日期是否已满 预约状态是否开启
     */
    private void checkAppointDateAndStatus(Date appointDate, Integer timePeriod) {
        ExtendedAppointManage extendedAppointManage = getExtendedAppointManage(appointDate, timePeriod);
        Assert.isTrue(extendedAppointManage.getEnabled(),"该预约日期暂未开放");
        Assert.isTrue(extendedAppointManage.getAppointNum() < extendedAppointManage.getTopLimit(),"预约已满");
    }

    /**
     * 根据预约日期和时间段获取预约管理信息
     */
    private ExtendedAppointManage getExtendedAppointManage(Date appointDate, Integer timePeriod) {
        AppointManageQuery amq = new AppointManageQuery();
        amq.setTimePeriod(timePeriod);
        amq.setAppointDate(DateUtil.getStartTime(appointDate));
        return appointManageService.queryOne(amq);
    }

    /**
     * 封装预约数据
     */
    private void assign(Appointment appointment, Date appointDate, Integer timePeriod, Long orderId, Long userId, Long productId, Long shopId) {
        appointment.setAppointDate(DateUtil.getStartTime(appointDate));
        appointment.setTimePeriod(timePeriod);
        appointment.setState(Appointment.STATE_APPOINTED);
        appointment.setOrderId(orderId);
        appointment.setUserId(userId);
        appointment.setProductId(productId);
        appointment.setShopId(shopId);
    }

    /**
     * 检查是否大于当前时间 并且数据库中是否有这个日期
     */
    private void checkDate(Integer timePeriod, Date appointDate) {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setStartAppointDate(appointDate);
        qo.setTimePeriod(timePeriod);
        qo.setAppointDate(appointDate);
        int count = appointManageService.count(qo);
        Assert.isTrue(count > 0,"无法预约以前的日期，或数据库中没有这个日期");
    }

    @Override
    public void update(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        this.checkUpdate(appointment,timePeriod,appointDate);
        appointment.setTimePeriod(timePeriod);
        appointment.setAppointDate(appointDate);
        this.update(appointment);
    }

    private void checkUpdate(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        Assert.notNull(appointment,"没有预约信息");
        Assert.notNull(timePeriod,"必须提供预约时间段");
        Assert.notNull(appointDate,"必须提供预约日期");
        Assert.isTrue(Appointment.STATE_APPOINTED.equals(appointment.getState()),"该预约已完成");
        this.checkDate(timePeriod,DateUtil.getStartTime(appointDate));
        this.checkAppointDateAndStatus(appointDate,timePeriod);
        this.checkShopValid(appointment.getShopId());
    }


    @Override
    public void delete(Long userId, Long productId, Long shopId, Long dicItemId) {

    }



    @Override
    public List<? extends Appointment> listAll(Long[] appointmentIds) {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setIds(appointmentIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedAppointment> listAll() {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public List<ExtendedAppointment> getByUser(Long userId, Long orderId) {
        Assert.notNull(userId,"必须提供用户id");
        Assert.notNull(orderId,"必须提供订单id");
        AppointmentQuery qo = new AppointmentQuery();
        qo.setUserId(userId);
        qo.setOrderId(orderId);
        return appointmentMapper.query(qo);
    }

    @Override
    public List<AppointDateVo> handleData(List<ExtendedAppointManage> appointManageList) {
        return appointManageList.stream().map(item -> {
            AppointDateVo vo = new AppointDateVo();
            vo.setAppointDate(item.getAppointDate());
            vo.setTimePeriod(item.getTimePeriod());
            vo.setFull(item.getAppointNum().equals(item.getTopLimit()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Pagination<AppointRecordVo> record(AppointmentQuery qo) {
        int rows = appointmentMapper.countRecord(qo);
        List<AppointRecordVo> datas = ((rows == 0) ? new ArrayList<>() : appointmentMapper.record(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public void update(Appointment appointment, Integer state) {
        Assert.notNull(appointment,"必须提供预约信息");
        Assert.notNull(state,"必须提供预约状态");
        appointment.setState(state);
        this.update(appointment);
    }

    @Override
    public AppointRecordVo getInfo(Long id) {
        return appointmentMapper.getInfo(id);
    }


}
