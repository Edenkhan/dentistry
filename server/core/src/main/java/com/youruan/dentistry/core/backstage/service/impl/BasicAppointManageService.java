
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.mapper.AppointManageMapper;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.AppointManageVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.DateUtil;
import com.youruan.dentistry.core.base.utils.DateUtils;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicAppointManageService
        implements AppointManageService {

    private final AppointManageMapper appointManageMapper;
    private final ShopService shopService;
    private final OrdersService ordersService;

    public BasicAppointManageService(AppointManageMapper appointManageMapper, ShopService shopService, OrdersService ordersService) {
        this.appointManageMapper = appointManageMapper;
        this.shopService = shopService;
        this.ordersService = ordersService;
    }

    @Override
    public AppointManage get(Long id) {
        return appointManageMapper.get(id);
    }

    protected void update(AppointManage appointManage) {
        int affected = appointManageMapper.update(appointManage);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        appointManage.setVersion((appointManage.getVersion() + 1));
    }

    protected AppointManage add(AppointManage appointManage) {
        appointManage.setCreatedDate(new Date());
        appointManageMapper.add(appointManage);
        return appointManage;
    }

    @Override
    public List<ExtendedAppointManage> listAll(AppointManageQuery qo) {
        qo.setMaxPageSize();
        return appointManageMapper.query(qo);
    }

    @Override
    public ExtendedAppointManage queryOne(AppointManageQuery qo) {
        qo.setPageSize(1);
        List<ExtendedAppointManage> datas = appointManageMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedAppointManage> query(AppointManageQuery qo) {
        int rows = getRows(qo);
        List<ExtendedAppointManage> datas = ((rows == 0) ? new ArrayList<>() : appointManageMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    /**
     * 查询7天范围内，数据库存在多少条数据
     */
    private int getRows(AppointManageQuery qo) {
        qo.setMaxPageSize();
        qo.setStartAppointDate(DateUtil.getStartTime(new Date()));
        qo.setEndAppointDate(DateUtil.getStartTime(DateUtils.addDays(new Date(),6)));
        return appointManageMapper.count(qo);
    }

    @Override
    public int count(AppointManageQuery qo) {
        return appointManageMapper.count(qo);
    }

    @Override
    public void create(List<AppointManage> appointManageList) {
        Assert.isTrue(!CollectionUtils.isEmpty(appointManageList),"必须提供预约管理集合");
        this.batch(appointManageList);
    }

    /**
     * 批量添加
     */
    private void batch(List<AppointManage> appointManageList) {
        appointManageList.stream().forEach(item -> item.setCreatedDate(new Date()));
        appointManageMapper.addBatch(appointManageList);
    }


    @Override
    public void update(AppointManage appointManage, Boolean enabled) {
        this.checkUpdate(appointManage, enabled);
        this.assign(appointManage, enabled);
        update(appointManage);
    }


    /**
     *
     */
    private void checkAdd(String name, String mark) {
//        this.checkParam(name, mark);
//        AppointManageQuery qo = new AppointManageQuery();
//        qo.setName(name);
//        int count = appointManageMapper.count(qo);
//        Assert.isTrue(count == 0,"字典名称重复");
//        qo = new AppointManageQuery();
//        qo.setMark(mark);
//        count = appointManageMapper.count(qo);
//        Assert.isTrue(count == 0,"字典标识重复");
    }

    /**
     *
     */
    private void checkUpdate(AppointManage appointManage, Boolean enabled) {
        Assert.notNull(appointManage,"必须提供预约管理");
        this.checkParam(enabled);
    }

    private void checkParam(Boolean enabled) {
        Assert.notNull(enabled, "必须提供预约状态");
    }

    /**
     * 封装数据
     */
    private void assign(AppointManage appointManage, Boolean enabled) {
        appointManage.setEnabled(enabled);
    }

    @Override
    public List<? extends AppointManage> listAll(Long[] appointManageIds) {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setIds(appointManageIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedAppointManage> listAll() {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public void generate() {
        List<ExtendedShop> shops = shopService.listAll();
        Assert.isTrue(!CollectionUtils.isEmpty(shops),"当前没有门店");
        List<AppointManage> appointManageList = new ArrayList<>();
        shops.forEach(item -> {
            generate(appointManageList, AppointManage.TIME_PERIOD_AM, item.getId(),0);
            generate(appointManageList, AppointManage.TIME_PERIOD_PM, item.getId(),0);
        });

        appointManageMapper.addBatch(appointManageList);
    }

    /**
     * 生成days天数据
     * @Param start 表示从第几天开始
     */
    private void generate(List<AppointManage> appointManageList, Integer timePeriod, Long shopId, Integer start) {
        AppointManage appointManage;
        for (int i = start; i < 7; i++) {
            appointManage = new AppointManage();
            appointManage.setCreatedDate(new Date());
            appointManage.setTopLimit(AppointManage.DEFAULT_TOPLIMIT);
            appointManage.setAppointNum(0);
            appointManage.setAppointDate(DateUtil.getStartTime(DateUtils.addDays(new Date(), i)));
            appointManage.setTimePeriod(timePeriod);
            appointManage.setEnabled(true);
            appointManage.setShopId(shopId);
            appointManageList.add(appointManage);
        }
    }

    @Override
    public List<Object> handleData(List<ExtendedAppointManage> appointManageList) {
//        List<AppointManageVo> voList = new ArrayList<>();
//        List<ExtendedAppointManage> amList = appointManageList.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_AM)).collect(Collectors.toList());
//        AppointManageVo vo1 = new AppointManageVo();
//        vo1.setPeriod(AppointManage.TIME_PERIOD_AM);
//        setup(vo1, amList);
//        List<ExtendedAppointManage> pmList = appointManageList.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_PM)).collect(Collectors.toList());
//        AppointManageVo vo2 = new AppointManageVo();
//        vo2.setPeriod(AppointManage.TIME_PERIOD_PM);
//        setup(vo2, pmList);
//        voList.add(vo1);
//        voList.add(vo2);

        List<Object> list = new ArrayList<>();
        List<ExtendedAppointManage> amList = appointManageList.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_AM)).collect(Collectors.toList());
        List<ExtendedAppointManage> pmList = appointManageList.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_PM)).collect(Collectors.toList());
        list.add(amList);
        list.add(pmList);

        return list;
    }

    /**
     * 将集合数据设置到对象
     */
    private void setup(AppointManageVo vo, List<ExtendedAppointManage> list) {
        for (int i = 0; i < list.size(); i++) {
            switch (i) {
                case 0:
                    vo.setDay1(list.get(i));
                    break;
                case 1:
                    vo.setDay2(list.get(i));
                    break;
                case 2:
                    vo.setDay3(list.get(i));
                    break;
                case 3:
                    vo.setDay4(list.get(i));
                    break;
                case 4:
                    vo.setDay5(list.get(i));
                    break;
                case 5:
                    vo.setDay6(list.get(i));
                    break;
                case 6:
                    vo.setDay7(list.get(i));
                    break;
            }
        }
    }

    @Override
    public AppointManage getOneByShopId(Long shopId) {
        AppointManageQuery qo = new AppointManageQuery();
        qo.setShopId(shopId);
        return this.queryOne(qo);
    }

    @Override
    public void update(AppointManage appointManage, Integer topLimit) {
        Assert.notNull(appointManage,"必须提供预约管理");
        Assert.notNull(topLimit,"必须提供预约人数上限");
        appointManage.setTopLimit(topLimit);
        this.updateTopLimit(appointManage);
    }

    @Override
    public void generate7Days(Long shopId) {
        Assert.notNull(shopId,"必须提供门店id");
        // 获取数据条目数 以(天)为单位
        AppointManageQuery qo = new AppointManageQuery();
        qo.setShopId(shopId);
        int rows = this.getRows(qo);
        int days = rows / 2;
        if(days >= 7) return;
        List<AppointManage> list = new ArrayList<>();
        this.generate(list,AppointManage.TIME_PERIOD_AM,shopId,days);
        this.generate(list,AppointManage.TIME_PERIOD_PM,shopId,days);
        this.addBatch(list);
    }

    private void addBatch(List<AppointManage> list) {
        appointManageMapper.addBatch(list);
    }

    @Override
    public void increAppointNum(AppointManage appointManage) {
        Assert.notNull(appointManage,"必须提供预约管理数据");
        appointManage.setAppointNum(appointManage.getAppointNum() + 1);
        this.update(appointManage);
    }

    @Override
    public void decreAppointNum(AppointManage appointManage) {
        Assert.notNull(appointManage,"必须提供预约管理数据");
        Assert.isTrue(appointManage.getAppointNum()>0,"当前日期没有人预约");
        appointManage.setAppointNum(appointManage.getAppointNum() - 1);
        this.update(appointManage);
    }

    @Override
    @Transactional
    public void updateTopLimit(AppointManage appointManage, Integer amTopLimit, Integer pmTopLimit) {
        this.checkUpdateTopLimit(appointManage,amTopLimit,pmTopLimit);
        // 上午
        appointManage.setTopLimit(amTopLimit);
        appointManage.setTimePeriod(AppointManage.TIME_PERIOD_AM);
        this.updateTopLimit(appointManage);
        // 下午
        appointManage.setTopLimit(pmTopLimit);
        appointManage.setTimePeriod(AppointManage.TIME_PERIOD_PM);
        this.updateTopLimit(appointManage);
    }

    @Override
    public List<ExtendedAppointManage> getAppointDateList(Long orderId) {
        Orders orders = ordersService.get(orderId);
        Assert.notNull(orders,"必须提供订单");
        Shop shop = shopService.get(orders.getShopId());
        Assert.notNull(shop,"必须提供门店");
        Assert.isTrue(shop.getEnabled(),"该门店已关闭预约");
        AppointManageQuery qo = new AppointManageQuery();
        qo.setStartAppointDate(DateUtil.getStartTime(new Date()));
        qo.setShopId(shop.getId());
        qo.setEnabled(true);
        return this.listAll(qo);
    }

    @Override
    public Boolean checkFull(Long orderId, Date appointDate, Integer timePeriod) {
        Assert.notNull(orderId,"必须提供订单id");
        Assert.notNull(appointDate,"必须提供预约日期");
        Assert.notNull(timePeriod,"必须提供预约时间段");
        Orders orders = ordersService.get(orderId);
        Assert.notNull(orders,"必须提供订单");
        AppointManageQuery qo = new AppointManageQuery();
        qo.setAppointDate(DateUtil.getStartTime(appointDate));
        qo.setTimePeriod(timePeriod);
        qo.setShopId(orders.getShopId());
        qo.setEnabled(true);
        ExtendedAppointManage extendedAppointManage = this.queryOne(qo);
        Assert.notNull(extendedAppointManage,"必须提供预约管理");
        return extendedAppointManage.getTopLimit() - extendedAppointManage.getAppointNum() <= 0;
    }

    @Override
    public List<Integer> getValidPeriod(Long orderId, Date appointDate) {
        Assert.notNull(orderId,"必须提供订单id");
        Assert.notNull(appointDate,"必须提供预约日期");
        Orders orders = ordersService.get(orderId);
        Assert.notNull(orders,"必须提供订单");
        Assert.notNull(orders.getShopId(),"必须提供门店id");
        AppointManageQuery qo = new AppointManageQuery();
        qo.setEnabled(true);
        qo.setShopId(orders.getShopId());
        qo.setAppointDate(DateUtil.getStartTime(appointDate));
        return this.listAll(qo).stream()
                .map(ExtendedAppointManage::getTimePeriod)
                .collect(Collectors.toList());
    }

    private void checkUpdateTopLimit(AppointManage appointManage, Integer amTopLimit, Integer pmTopLimit) {
        Assert.notNull(appointManage,"必须提供预约管理");
        Assert.isTrue(amTopLimit==null || amTopLimit>0,"预约上限(上午)必须大于0");
        Assert.isTrue(pmTopLimit==null || pmTopLimit>0,"预约上限(下午)必须大于0");
    }

    /**
     * 修改门店预约上限
     */
    private void updateTopLimit(AppointManage appointManage) {
        int affected = appointManageMapper.updateTopLimit(appointManage);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        appointManage.setVersion((appointManage.getVersion() + 1));
    }
}
