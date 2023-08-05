package com.dev.happyapi.core.storage;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Profile("default")
public class GCPStorageConfiguration {

    @Value("${storage.gcp_storage.key_file}")
    private String keyFile;

    @Value("${storage.gcp_storage.project_id}")
    private String gcpStorageProjectId;

    @Bean
    Storage buildGCPStorage() throws IOException {
        return StorageOptions.newBuilder()
                .setProjectId(gcpStorageProjectId)
                .setCredentials(buildCredentials())
                .build()
                .getService();
    }

    private Credentials buildCredentials() throws IOException {
        return GoogleCredentials.fromStream(new FileInputStream(keyFile));
    }
}
