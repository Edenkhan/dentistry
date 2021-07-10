package com.youruan.dentistry.core.frontdesk.mapper;


import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedAppointment;

import java.util.List;


public interface AppointmentMapper {
    /**
     * 获取单条记录
     */
    public Appointment get(Long id);
    /**
     * 修改
     */
    public int update(Appointment appointment);
    /**
     * 添加
     */
    public int add(Appointment appointment);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(AppointmentQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedAppointment> query(AppointmentQuery qo);

    /**
     * 联表查询预约记录数
     */
    int countRecord(AppointmentQuery qo);

    /**
     * 条件查询 预约记录
     */
    List<AppointRecordVo> record(AppointmentQuery qo);
}
