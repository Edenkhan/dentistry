package com.youruan.dentistry.core.frontdesk.service.impl;


import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import com.youruan.dentistry.core.backstage.vo.ExtendedReport;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicAppointmentService implements AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final OrdersService ordersService;
    private final AppointManageService appointManageService;
    private final ShopService shopService;
    private final RedeemCodeService redeemCodeService;
    private final ReportService reportService;

    public BasicAppointmentService(AppointmentMapper appointmentMapper, OrdersService ordersService, AppointManageService appointManageService, ShopService shopService, RedeemCodeService redeemCodeService, ReportService reportService) {
        this.appointmentMapper = appointmentMapper;
        this.ordersService = ordersService;
        this.appointManageService = appointManageService;
        this.shopService = shopService;
        this.redeemCodeService = redeemCodeService;
        this.reportService = reportService;
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
        if(orders.getIsRedeemOrder()) {
            // 如果是兑换码订单，将此兑换码设置为已使用
            RedeemCodeQuery qo = new RedeemCodeQuery();
            qo.setOrderId(orderId);
            ExtendedRedeemCode redeemCode = redeemCodeService.queryOne(qo);
            redeemCodeService.redeemCompleted(redeemCode);
        }
        this.assign(appointment, appointDate, timePeriod, orderId, userId, orders.getProductId(), orders.getShopId());
        appointment = this.add(appointment);
        Assert.notNull(appointment, "预约失败");
        // 增加订单预约次数
        ordersService.increAppointNum(orders);
        // 订单 预约进行中
        ordersService.appointProgress(orders);
        // 增加当前预约日期 预约数量
        AppointManage appointManage = this.getAppointManage(appointDate, timePeriod, orders.getShopId());
        appointManageService.increAppointNum(appointManage);
        // 增加门店预约次数
        Shop shop = shopService.get(orders.getShopId());
        shopService.increAppointNum(shop);
        return appointment;
    }

    /**
     * 根据日期和时段获取预约管理
     */
    private AppointManage getAppointManage(Date appointDate, Integer timePeriod, Long shopId) {
        ExtendedAppointManage extendedAppointManage = this.getExtendedAppointManage(appointDate, timePeriod, shopId);
        AppointManage appointManage = new AppointManage();
        BeanUtils.copyProperties(extendedAppointManage, appointManage);
        return appointManage;
    }

    private void checkAdd(Date appointDate, Integer timePeriod, Long orderId, Long userId) {
        Assert.notNull(appointDate, "必须提供预约日期");
        Assert.notNull(timePeriod, "必须提供预约时间段");
        Assert.notNull(orderId, "必须提供订单id");
        Assert.notNull(userId, "必须提供用户id");
        Orders orders = ordersService.get(orderId);
        // 校验日期
        this.checkDate(timePeriod, DateUtil.getStartTime(appointDate), orders.getShopId());
        // 校验用户预约状态
        this.checkUserAppoint(userId);
        // 校验订单预约次数
        this.checkOrderAppointNum(orderId);
        // 校验预约日期、预约状态
        this.checkAppointDateAndStatus(appointDate, timePeriod, orders.getShopId());
        // 校验门店总预约数
        this.checkShopValid(ordersService.get(orderId).getShopId());
    }

    /**
     * 用户是否已经有过预约
     */
    private void checkUserAppoint(Long userId) {
        AppointmentQuery aq = new AppointmentQuery();
        aq.setUserId(userId);
        aq.setAppointState(Appointment.APPOINT_STATE_APPOINTED);
        int count = this.count(aq);
        Assert.isTrue(count == 0, "该用户已经有过预约");
    }

    /**
     * 当前订单是否还有预约次数
     */
    private void checkOrderAppointNum(Long orderId) {
        Orders orders = ordersService.get(orderId);
        Assert.notNull(orders, "必须提供订单");
        Assert.isTrue(orders.getAppointNum() < orders.getTotalNum(), "订单预约次数已用完");
    }

    /**
     * 门店总预约数量是否已满
     */
    private void checkShopValid(Long shopId) {
        Shop shop = shopService.get(shopId);
        Assert.isTrue(shop.getAppointNum() < shop.getTotalNum(), "门店总预约次数已满");
    }

    /**
     * 该预约日期是否已满 预约状态是否开启
     */
    private void checkAppointDateAndStatus(Date appointDate, Integer timePeriod, Long shopId) {
        ExtendedAppointManage extendedAppointManage = getExtendedAppointManage(appointDate, timePeriod, shopId);
        Assert.notNull(extendedAppointManage, "必须提供预约管理");
        Assert.isTrue(extendedAppointManage.getEnabled(), "该预约日期暂未开放");
        Assert.isTrue(extendedAppointManage.getAppointNum() < extendedAppointManage.getTopLimit(), "预约已满");
    }

    /**
     * 根据预约日期和时间段获取预约管理信息
     */
    private ExtendedAppointManage getExtendedAppointManage(Date appointDate, Integer timePeriod, Long shopId) {
        AppointManageQuery amq = new AppointManageQuery();
        amq.setTimePeriod(timePeriod);
        amq.setAppointDate(DateUtil.getStartTime(appointDate));
        amq.setShopId(shopId);
        return appointManageService.queryOne(amq);
    }

    /**
     * 封装预约数据
     */
    private void assign(Appointment appointment, Date appointDate, Integer timePeriod, Long orderId, Long userId, Long productId, Long shopId) {
        appointment.setAppointDate(DateUtil.getStartTime(appointDate));
        appointment.setTimePeriod(timePeriod);
        appointment.setAppointState(Appointment.APPOINT_STATE_APPOINTED);
        appointment.setReportStatus(Appointment.REPORT_STATUS_NOT);
        appointment.setOrderId(orderId);
        appointment.setUserId(userId);
        appointment.setProductId(productId);
        appointment.setShopId(shopId);
    }

    /**
     * 校验是否大于当前时间 并且数据库中是否有这个日期
     */
    private void checkDate(Integer timePeriod, Date appointDate, Long shopId) {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setStartAppointDate(appointDate);
        qo.setTimePeriod(timePeriod);
        qo.setAppointDate(appointDate);
        qo.setShopId(shopId);
        int count = appointManageService.count(qo);
        Assert.isTrue(count > 0, "无法预约该门店以前的日期，或该门店数据库中没有这个日期");
    }

    @Override
    @Transactional
    public void update(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        this.checkUpdate(appointment, timePeriod, appointDate);
        // 获取修改之前预约管理
        AppointManage appointManage = this.getAppointManage(appointment.getAppointDate(), appointment.getTimePeriod(), appointment.getShopId());
        // 扣减之前预约日期 预约数量
        appointManageService.decreAppointNum(appointManage);
        // 获取当前预约管理
        appointManage = this.getAppointManage(appointDate, timePeriod, appointment.getShopId());
        // 增加当前预约日期 预约数量
        appointManageService.increAppointNum(appointManage);
        appointment.setTimePeriod(timePeriod);
        appointment.setAppointDate(appointDate);
        this.update(appointment);
    }

    private void checkUpdate(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        Assert.notNull(appointment, "没有预约信息");
        Assert.notNull(timePeriod, "必须提供预约时间段");
        Assert.notNull(appointDate, "必须提供预约日期");
        Assert.isTrue(Appointment.APPOINT_STATE_APPOINTED.equals(appointment.getAppointState()), "该预约已完成");
        // 校验预约日期是否有改动
        this.checkChange(appointment, timePeriod, appointDate);
        // 校验日期
        this.checkDate(timePeriod, DateUtil.getStartTime(appointDate), appointment.getShopId());
        // 校验预约日期、预约状态
        this.checkAppointDateAndStatus(appointDate, timePeriod, appointment.getShopId());
        // 校验门店预约总数量
        this.checkShopValid(appointment.getShopId());
    }

    /**
     * 校验预约日期是否有改动
     */
    private void checkChange(ExtendedAppointment appointment, Integer timePeriod, Date appointDate) {
        Assert.isTrue(!timePeriod.equals(appointment.getTimePeriod())
                || appointDate.compareTo(appointment.getAppointDate())!=0,"当前日期未作修改");
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
        Assert.notNull(userId, "必须提供用户id");
        Assert.notNull(orderId, "必须提供订单id");
        AppointmentQuery qo = new AppointmentQuery();
        qo.setUserId(userId);
        qo.setOrderId(orderId);
        return appointmentMapper.query(qo);
    }

    @Override
    public AppointDateVo handleData(ExtendedAppointment extendedAppointment) {
        AppointManageQuery amq = new AppointManageQuery();
        amq.setShopId(extendedAppointment.getShopId());
        amq.setAppointDate(extendedAppointment.getAppointDate());
        amq.setTimePeriod(extendedAppointment.getTimePeriod());
        amq.setEnabled(true);
        ExtendedAppointManage extendedAppointManage = appointManageService.queryOne(amq);
        Assert.notNull(extendedAppointManage,"必须提供预约管理");
        AppointDateVo vo = new AppointDateVo();
        vo.setAppointDate(extendedAppointment.getAppointDate());
        vo.setTimePeriod(extendedAppointment.getTimePeriod());
        vo.setIsFull(extendedAppointManage.getTopLimit() - extendedAppointManage.getAppointNum() <= 0);
        return vo;
    }

    @Override
    public Pagination<AppointRecordVo> record(AppointmentQuery qo) {
        int rows = appointmentMapper.countRecord(qo);
        List<AppointRecordVo> datas = ((rows == 0) ? new ArrayList<>() : appointmentMapper.record(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public AppointRecordVo getInfo(Long id) {
        return appointmentMapper.getInfo(id);
    }

    @Override
    public void reportCompleted(Appointment appointment) {
        Assert.notNull(appointment, "必须提供预约信息");
        Assert.isTrue(Appointment.REPORT_STATUS_NOT.equals(appointment.getReportStatus()), "当前报告状态不是未上传");
        appointment.setReportStatus(Appointment.REPORT_STATUS_OK);
        this.update(appointment);
    }

    @Override
    @Transactional
    public void appointCompleted(Appointment appointment) {
        Assert.notNull(appointment, "必须提供预约信息");
        Assert.isTrue(Appointment.APPOINT_STATE_APPOINTED.equals(appointment.getAppointState()), "当前不是在预约中状态");
        // 校验当前预约下的报告是否全部同步
        this.checkReportSync(appointment.getId());
        // 预约完成
        appointment.setAppointState(Appointment.APPOINT_STATE_FINISH);
        appointment.setReportStatus(Appointment.REPORT_STATUS_OK);
        this.update(appointment);
        // 訂單修改為 未預約 已完成
        Orders orders = ordersService.get(appointment.getOrderId());
        ordersService.appointCompleted(orders);
    }

    @Override
    public ExtendedAppointment getAppointing(Long orderId) {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setOrderId(orderId);
        qo.setAppointState(Appointment.APPOINT_STATE_APPOINTED);
        int count = this.count(qo);
        Assert.isTrue(count <= 1,"当前正在预约中的预约大于1");
        return this.queryOne(qo);
    }

    /**
     * 校验当前预约下的报告是否全部同步
     */
    private void checkReportSync(Long appointId) {
        ReportQuery qo = new ReportQuery();
        qo.setAppointId(appointId);
        List<ExtendedReport> reportList = reportService.listAll(qo);
        Assert.isTrue(!CollectionUtils.isEmpty(reportList),"当前没有上传报告");
        reportList.forEach(item -> Assert.isTrue(item.getSync(),"当前有报告未同步，无法完成预约"));
    }


}
