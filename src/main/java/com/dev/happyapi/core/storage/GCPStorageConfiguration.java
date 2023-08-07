package com.dev.happyapi.core.storage;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
@Profile("default")
public class GCPStorageConfiguration {

    @Value("${storage.gcp_storage.key_file}")
    private String keyFile;

    @Value("${storage.gcp_storage.project_id}")
    private String gcpStorageProjectId;

    @Bean
    Storage buildGCPStorage() {
        try {
            log.info("Building development Google Cloud Storage instance with credentials");
            Credentials credentials = buildCredentials();
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId(gcpStorageProjectId)
                    .setCredentials(credentials)
                    .build()
                    .getService();

            log.info("Successful build Google Cloud Storage instance");

            return storage;
        } catch (Exception e) {
            log.error("Error on building Google Cloud Storage instance", e);
            throw new RuntimeException(e);
        }
    }

    private Credentials buildCredentials() throws IOException {
        return GoogleCredentials.fromStream(new FileInputStream(keyFile));
    }
}
