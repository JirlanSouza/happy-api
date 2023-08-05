package com.dev.happyapi.core.storage;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class ProdGCPStorageConfiguration {
    @Bean
    Storage buildGCPStorage() {
        return StorageOptions.getDefaultInstance()
                .getService();
    }
}
