package com.youruan.dentistry.portal.base.utils;


import com.google.common.net.HttpHeaders;
import com.youruan.dentistry.core.base.storage.UploadFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WebUtils {

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        }
        return xForwardedForHeader.split(",")[0].trim();
    }

    public static UploadFile getUploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriginalFilename(file.getOriginalFilename());
        uploadFile.setSize(file.getSize());
        uploadFile.setInputStream(file.getInputStream());
        uploadFile.setExtensionName(FilenameUtils.getExtension(uploadFile.getOriginalFilename()).toLowerCase());
        return uploadFile;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

}
