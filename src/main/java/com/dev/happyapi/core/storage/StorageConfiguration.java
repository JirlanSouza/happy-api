package com.dev.happyapi.core.storage;

import com.dev.happyapi.shared.storage.gcp.GCPStorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration implements GCPStorageProperties {

    @Value("${storage.gcp_storage.bucket}")
    private String bucket;

    @Override
    public String getBucket() {
        return bucket;
    }

}

