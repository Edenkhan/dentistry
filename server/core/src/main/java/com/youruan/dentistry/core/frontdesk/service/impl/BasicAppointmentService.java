package com.youruan.dentistry.core.frontdesk.service.impl;


import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
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

    public BasicAppointmentService(AppointmentMapper appointmentMapper, OrdersService ordersService, AppointManageService appointManageService) {
        this.appointmentMapper = appointmentMapper;
        this.ordersService = ordersService;
        this.appointManageService = appointManageService;
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
        this.assign(appointment,appointDate,timePeriod, orderId,userId,orders.getProductId(),orders.getShopId());
        appointment = this.add(appointment);
        Assert.notNull(appointment,"预约失败");
        ordersService.updateAppointNum(orders);
        AppointManage appointManage = new AppointManage();
        BeanUtils.copyProperties(extendedAppointManage,appointManage);
        appointManageService.updateAppointNum(appointManage);
        return appointment;
    }

    @Override
    public void update(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        Assert.notNull(appointment,"必须提供预约信息");
        Assert.notNull(timePeriod,"必须提供预约时间段");
        Assert.notNull(appointDate,"必须提供预约日期");
        this.checkDate(timePeriod,DateUtil.getStartTime(appointDate));
        appointment.setTimePeriod(timePeriod);
        appointment.setAppointDate(appointDate);
        this.update(appointment);
    }

    /**
     * 检查数据库中是否有这个日期
     */
    private void checkDate(Integer timePeriod, Date appointDate) {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setTimePeriod(timePeriod);
        qo.setAppointDate(appointDate);
        int count = appointManageService.count(qo);
        Assert.isTrue(count > 0,"数据库中没有这个日期");
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

    private Orders orders;
    private ExtendedAppointManage extendedAppointManage;
    private void checkAdd(Date appointDate, Integer timePeriod, Long orderId, Long userId) {
        Assert.notNull(appointDate,"必须提供预约日期");
        Assert.notNull(timePeriod,"必须提供预约时间段");
        Assert.notNull(orderId,"必须提供订单id");
        Assert.notNull(userId,"必须提供用户id");
        // 检查日期
        this.checkDate(timePeriod,DateUtil.getStartTime(appointDate));
        //判断用户是否已经有过预约
        AppointmentQuery qo = new AppointmentQuery();
        qo.setUserId(userId);
        qo.setState(Appointment.STATE_APPOINTED);
        int count = this.count(qo);
        Assert.isTrue(count == 0,"该用户已经有过预约");
        // 判断预约当前订单是否还有预约次数
        orders = ordersService.get(orderId);
        Assert.notNull(orders,"必须提供订单");
        Assert.isTrue(orders.getAppointNum()<orders.getTotalNum(),"订单预约次数已用完");
        // 判断门店是否还能预约
        AppointManageQuery qo2 = new AppointManageQuery();
        qo2.setTimePeriod(timePeriod);
        qo2.setAppointDate(DateUtil.getStartTime(appointDate));
        extendedAppointManage = appointManageService.queryOne(qo2);
        Assert.isTrue(extendedAppointManage.getAppointNum() < extendedAppointManage.getTopLimit(),"预约已满");
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


}
