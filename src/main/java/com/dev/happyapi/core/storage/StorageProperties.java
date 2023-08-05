package com.dev.happyapi.core.storage;

import com.dev.happyapi.orphanage.services.OrphanagesImagesStorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties implements OrphanagesImagesStorageProperties {

    @Value("${storage.orphanages.images.folder_name}")
    private String orphanageImagesFolder;

    @Override
    public String getOrphanageImagesFolderName() {
        return orphanageImagesFolder;
    }
}
