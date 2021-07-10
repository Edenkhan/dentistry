package com.youruan.dentistry.core.base.storage;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class DiskFileStorage implements FileStorage {

    private DiskFileStorageProperties diskFileStorageProperties;

    @Autowired
    public void setDiskFileStorageProperties(DiskFileStorageProperties diskFileStorageProperties) {
        this.diskFileStorageProperties = diskFileStorageProperties;
    }

    @Override
    public String store(UploadFile file, String directory) throws IOException {
        String filename = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        File destDirectory = new File(diskFileStorageProperties.getBaseDirectory(), directory);
        String filenameWithExtension = filename + "." + extension;
        File destFile = new File(destDirectory, filenameWithExtension);
        if (!destDirectory.exists() && !destDirectory.mkdirs()) {
            throw new IOException("文件目录创建失败");
        }
        FileOutputStream os = new FileOutputStream(destFile);
        IOUtils.copy(file.getInputStream(), os);
        os.close();
        return diskFileStorageProperties.getBaseUrl() + "/" + directory + "/" + filenameWithExtension;
    }

}