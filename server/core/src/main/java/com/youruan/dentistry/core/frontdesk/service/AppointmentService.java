package com.youruan.dentistry.core.frontdesk.service;


import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.vo.AppointDateVo;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedAppointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    /**
     * 根据id，获取单条记录
     */
    public Appointment get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedAppointment queryOne(AppointmentQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedAppointment> listAll(AppointmentQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedAppointment> query(AppointmentQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(AppointmentQuery qo);
    /**
     * 添加
     */
    Appointment create(Date appointDate, Integer timePeriod, Long orderId, Long userId);

    /**
     * 修改
     */
    void update(ExtendedAppointment appointment, Integer timePeriod, Date appointDate);

    /**
     * 删除
     */
    void delete(Long userId, Long productId, Long shopId, Long dicItemId);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Appointment> listAll(Long[] appointmentIds);
    /**
     * 返回所有记录
     */
    List<ExtendedAppointment> listAll();

    /**
     * 查询当前用户的预约记录
     */
    List<ExtendedAppointment> getByUser(Long userId, Long orderId);

    /**
     * 获取可预约的情况
     */
    List<AppointDateVo> handleData(List<ExtendedAppointManage> appointManageList);

    /**
     * 查询预约记录
     */
    Pagination<AppointRecordVo> record(AppointmentQuery qo);

    /**
     * 预约完成
     */
    void update(Appointment appointment, Integer state);

    /**
     * 获取预约记录
     */
    AppointRecordVo getInfo(Long id);
}
