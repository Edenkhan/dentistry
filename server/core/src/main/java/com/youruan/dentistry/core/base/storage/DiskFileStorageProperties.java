package com.youruan.dentistry.core.base.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dentistry.core.base.storage.disk")
public class DiskFileStorageProperties {

    /**
     * 根目录
     */
    private String baseUrl;

    /**
     * 根目录
     */
    private String baseDirectory;

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}