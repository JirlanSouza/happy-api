package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.orphanage.dtos.FileDto;
import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrphanageImageUploadService {
    private final FileStorage storage;
    private final String orphanageImagesFolder;

    public OrphanageImageUploadService(FileStorage storage, OrphanagesImagesStorageProperties imagesStorageProperties) {
        this.storage = storage;
        this.orphanageImagesFolder = imagesStorageProperties.getOrphanageImagesFolderName();
    }

    public List<CreateOrphanageImageDto> saveImages(List<FileDto> images) {
        List<CreateOrphanageImageDto> imagesDto = new ArrayList<>();

        long time = new Date().getTime();

        images.stream().filter(i -> i.size() > 0).forEach(i -> {
            var imageFileInfo = new FileInfo(
                    "%d-%s".formatted(time, i.name()),
                    i.size(),
                    i.mimeType()
            );

            String imageUrl = storage.storeFile(orphanageImagesFolder, imageFileInfo, i.data());

            imagesDto.add(
                    new CreateOrphanageImageDto(
                            imageFileInfo.name(),
                            imageFileInfo.mimeType(),
                            imageFileInfo.size(),
                            imageUrl
                    )
            );

        });

        return imagesDto;
    }
}
