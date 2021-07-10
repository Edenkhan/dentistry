package com.youruan.dentistry.core.base.storage;

import java.io.IOException;

public interface FileStorage {

    String store(UploadFile file, String directory) throws IOException;

}