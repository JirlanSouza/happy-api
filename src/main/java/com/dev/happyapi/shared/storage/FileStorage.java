package com.dev.happyapi.shared.storage;

import java.io.InputStream;

public interface FileStorage {
    String storeFile(String folder, FileInfo info, InputStream stream) throws StoreFileException;
}
