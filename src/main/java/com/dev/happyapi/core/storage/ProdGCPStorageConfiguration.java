package com.dev.happyapi.core.storage;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("production")
public class ProdGCPStorageConfiguration {
    @Bean
    Storage buildGCPStorage() {
        try {
            log.info("Building production Google Cloud Storage instance with default instance");
            Storage storage = StorageOptions.getDefaultInstance().getService();
            log.info("Successful build Google Cloud Storage instance");

            return storage;
        } catch (Exception e) {
            log.error("Error on building Google Cloud Storage instance", e);
            throw new RuntimeException(e);
        }
    }
}
