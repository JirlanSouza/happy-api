package com.dev.happyapi.core.storage;

import com.dev.happyapi.shared.storage.gcp.GCPStorageProperties;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class StorageConfiguration implements GCPStorageProperties {

    @Value("${storage.gcp_storage.key_file}")
    private String keyFile;

    @Value("${storage.gcp_storage.project_id}")
    private String gcpStorageProjectId;

    @Value("${storage.gcp_storage.bucket}")
    private String bucket;

    @Bean
    Storage buildGCPStorage() throws IOException {
        return StorageOptions.newBuilder()
                .setProjectId(gcpStorageProjectId)
                .setCredentials(buildCredentials())
                .build()
                .getService();
    }

    @Override
    public String getBucket() {
        return bucket;
    }

    private Credentials buildCredentials() throws IOException {
        return GoogleCredentials.fromStream(new FileInputStream(keyFile));
    }
}

