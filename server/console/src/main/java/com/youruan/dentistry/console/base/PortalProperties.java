package com.youruan.dentistry.console.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "dentistry.portal")
@Component
public class PortalProperties {

    private String baseUrl;
    private String borrowRequestPagePath;
    private String borrowRequestPageUrl;

    public void setBorrowRequestPagePath(String borrowRequestPagePath) {
        this.borrowRequestPagePath = borrowRequestPagePath;
    }

    public String getBorrowRequestPagePath() {
        return borrowRequestPagePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setBorrowRequestPageUrl(String borrowRequestPageUrl) {
        this.borrowRequestPageUrl = borrowRequestPageUrl;
    }

    public String getBorrowRequestPageUrl() {
        return borrowRequestPageUrl;
    }
}
