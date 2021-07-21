
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.base.query.Pagination;

import java.util.Date;
import java.util.List;

public interface AppointManageService {

    /**
     * 根据id，获取单条记录
     */
    public AppointManage get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedAppointManage queryOne(AppointManageQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedAppointManage> listAll(AppointManageQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedAppointManage> query(AppointManageQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(AppointManageQuery qo);
    /**
     * 批量添加
     */
    void create(List<AppointManage> appointList);
    /**
     * 修改
     */
    void update(AppointManage appointManage, Boolean enabled);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends AppointManage> listAll(Long[] appointManageIds);
    /**
     * 返回所有记录
     */
    List<ExtendedAppointManage> listAll();

    /**
     * 生成7天数据
     */
    void generate();
    /**
     * 处理数据
     */
    List<Object> handleData(List<ExtendedAppointManage> appointManages);
    /**
     * 根据门店id获取预约上限
     */
    AppointManage getOneByShopId(Long shopId);
    /**
     * 修改门店预约上限
     */
    void update(AppointManage appointManage, Integer topLimit);
    /**
     * 检查当前门店在数据库是否有7天的数据
     */
    void generate7Days(Long shopId);
    /**
     * 增加已预约次数
     */
    void increAppointNum(AppointManage appointManage);
    /**
     * 减少已预约次数
     */
    void decreAppointNum(AppointManage appointManage);

    /**
     * 修改每天上午或下午的预约上限
     */
    void updateTopLimit(AppointManage appointManage, Integer amTopLimit, Integer pmTopLimit);

    /**
     * 获取客户端预约日期
     */
    List<ExtendedAppointManage> getAppointDateList(Long orderId);

    /**
     * 查看当前时段是否爆满 爆满返回true
     */
    Boolean checkFull(Long orderId, Date appointDate, Integer timePeriod);

    /**
     * 获取当前日期已启用的时段
     */
    List<Integer> getValidPeriod(Long orderId, Date appointDate);
}
