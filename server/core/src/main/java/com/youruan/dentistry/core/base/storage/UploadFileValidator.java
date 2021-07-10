package com.youruan.dentistry.core.base.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

public class UploadFileValidator {

    private String[] allowedExtensionNames;
    private long maxFileSize;

    public UploadFileValidator(String[] allowedExtensionNames, long maxFileSize) {
        this.allowedExtensionNames = allowedExtensionNames;
        this.maxFileSize = maxFileSize;
    }

    public void validate(UploadFile uploadFile) {
        if (!ArrayUtils.contains(allowedExtensionNames, uploadFile.getExtensionName().toLowerCase())) {
            throw new IllegalArgumentException("不支持的文件类型");
        }

        if (uploadFile.getSize() > maxFileSize) {
            throw new IllegalArgumentException("文件大小不能超过：" + FileUtils.byteCountToDisplaySize(maxFileSize));
        }
    }

}