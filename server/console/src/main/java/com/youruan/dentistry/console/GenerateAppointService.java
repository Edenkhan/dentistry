package com.youruan.dentistry.console;

import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.base.domain.BasicDomain;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenerateAppointService implements CommandLineRunner {

    private final AppointManageService appointManageService;
    private final ShopService shopService;

    public GenerateAppointService(AppointManageService appointManageService, ShopService shopService) {
        this.appointManageService = appointManageService;
        this.shopService = shopService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("***********启动任务***********");
        shopService.listAll().stream()
                .map(BasicDomain::getId)
                .forEach(appointManageService::generate7Days);
    }
}
