package com.youruan.dentistry.console;

import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.utils.DateUtil;
import com.youruan.dentistry.core.base.utils.DateUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class TaskConfig {

    private final AppointManageService appointManageService;
    private final ShopService shopService;

    public TaskConfig(AppointManageService appointManageService, ShopService shopService) {
        this.appointManageService = appointManageService;
        this.shopService = shopService;
    }

//    @Scheduled(cron = "0 0 0/2 * * ?")
    public void task() {
        System.out.println("***********定时任务触发！！！***********");
        AppointManageQuery qo = new AppointManageQuery();
        qo.setStartAppointDate(DateUtil.getStartTime(DateUtils.addDays(new Date(),6)));
        int count = appointManageService.count(qo);
        if (count == 0) {
            List<ExtendedShop> shops = shopService.listAll();
            Assert.isTrue(!CollectionUtils.isEmpty(shops),"当前没有门店");
            List<AppointManage> amList = shops.stream().map(item -> setup(item, AppointManage.TIME_PERIOD_AM)).collect(Collectors.toList());
            List<AppointManage> pmList = shops.stream().map(item -> setup(item, AppointManage.TIME_PERIOD_PM)).collect(Collectors.toList());
            appointManageService.create(ListUtils.union(amList,pmList));
        }
    }

    /**
     * 创建预约管理数据
     */
    private AppointManage setup(ExtendedShop item,Integer timePeriod) {
        AppointManage appointManage = new AppointManage();
        appointManage.setTopLimit(AppointManage.DEFAULT_TOPLIMIT);
        appointManage.setAppointNum(0);
        appointManage.setAppointDate(new Date());
        appointManage.setTimePeriod(timePeriod);
        appointManage.setEnabled(true);
        appointManage.setShopId(item.getId());
        return appointManage;
    }


}
