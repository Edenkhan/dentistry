
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.AppointManage;
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
import org.springframework.stereotype.Service;
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

    public BasicAppointManageService(AppointManageMapper appointManageMapper, ShopService shopService) {
        this.appointManageMapper = appointManageMapper;
        this.shopService = shopService;
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
        qo.setStartAppointDate(DateUtil.getStartTime(new Date()));
        qo.setEndAppointDate(DateUtil.getLastTime(DateUtils.addDays(new Date(),6)));
        return appointManageMapper.count(qo);
    }

    @Override
    public int count(AppointManageQuery qo) {
        return appointManageMapper.count(qo);
    }

    @Override
    public void create(List<AppointManage> appointList) {
        Assert.isTrue(!CollectionUtils.isEmpty(appointList),"必须提供预约管理集合");
        this.batch(appointList);
    }

    /**
     * 批量添加
     */
    private void batch(List<AppointManage> appointList) {
        appointList.stream().forEach(item -> item.setCreatedDate(new Date()));
        appointManageMapper.batch(appointList);
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
        List<AppointManage> appointManages = new ArrayList<>();
        shops.forEach(item -> {
            generate(appointManages, AppointManage.TIME_PERIOD_AM, item.getId(),0);
            generate(appointManages, AppointManage.TIME_PERIOD_PM, item.getId(),0);
        });

        appointManageMapper.batch(appointManages);
    }

    /**
     * 生成days天数据
     * @Param start 表示从第几天开始
     */
    private void generate(List<AppointManage> appointManages, Integer timePeriod, Long shopId, Integer start) {
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
            appointManages.add(appointManage);
        }
    }

    @Override
    public List<Object> handleData(List<ExtendedAppointManage> appointManages) {
//        List<AppointManageVo> voList = new ArrayList<>();
//        List<ExtendedAppointManage> amList = appointManages.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_AM)).collect(Collectors.toList());
//        AppointManageVo vo1 = new AppointManageVo();
//        vo1.setPeriod(AppointManage.TIME_PERIOD_AM);
//        setup(vo1, amList);
//        List<ExtendedAppointManage> pmList = appointManages.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_PM)).collect(Collectors.toList());
//        AppointManageVo vo2 = new AppointManageVo();
//        vo2.setPeriod(AppointManage.TIME_PERIOD_PM);
//        setup(vo2, pmList);
//        voList.add(vo1);
//        voList.add(vo2);

        List<Object> list = new ArrayList<>();
        List<ExtendedAppointManage> amList = appointManages.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_AM)).collect(Collectors.toList());
        List<ExtendedAppointManage> pmList = appointManages.stream().filter(item -> item.getTimePeriod().equals(AppointManage.TIME_PERIOD_PM)).collect(Collectors.toList());
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
    public void checkDataSource(AppointManageQuery qo) {
        Long shopId = qo.getShopId();
        Assert.notNull(shopId,"必须提供门店id");
        // 获取数据条目数 以(天)为单位
        int days = this.getRows(qo) / 2;
        if(days >= 7) return;
        List<AppointManage> list = new ArrayList<>();
        generate(list,AppointManage.TIME_PERIOD_AM,shopId,days);
        generate(list,AppointManage.TIME_PERIOD_PM,shopId,days);
        appointManageMapper.batch(list);
    }

    @Override
    public void updateAppointNum(AppointManage appointManage) {
        Assert.notNull(appointManage,"必须提供预约管理数据");
        appointManage.setAppointNum(appointManage.getAppointNum() + 1);
        this.update(appointManage);
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
