package com.youruan.dentistry.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.youruan.dentistry")
@EnableCaching
public class PortalApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(PortalApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
