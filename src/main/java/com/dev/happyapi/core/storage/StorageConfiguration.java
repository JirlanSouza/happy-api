package com.dev.happyapi.core.storage;

import com.dev.happyapi.shared.storage.gcp.GCPStorageProperties;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration implements GCPStorageProperties {

    @Value("${storage.gcp_storage.project_id}")
    private String gcpStorageProjectId;

    @Value("${storage.gcp_storage.bucket}")
    private String bucket;

    @Bean
    Storage buildGCPStorage() {
        return StorageOptions.newBuilder()
                .setProjectId(gcpStorageProjectId)
                .build()
                .getService();
    }

    @Override
    public String getBucket() {
        return bucket;
    }
}

