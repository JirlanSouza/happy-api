package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrphanageImageUploadService {
    private final FileStorage storage;
    private final String orphanageImagesFolder;

    public OrphanageImageUploadService(FileStorage storage, OrphanagesImagesStorageProperties imagesStorageProperties) {
        this.storage = storage;
        this.orphanageImagesFolder = imagesStorageProperties.getOrphanageImagesFolderName();
    }

    public List<CreateOrphanageImageDto> saveImages(List<MultipartFile> images) {
        log.info("Starting storing of {} Orphanage images", images.size());
        List<CreateOrphanageImageDto> imagesDto = new ArrayList<>();

        long time = new Date().getTime();

        images.forEach(i -> {
            if (!i.isEmpty()) {
                var imageFileInfo = new FileInfo(
                        "%d-%s".formatted(time, i.getOriginalFilename()),
                        i.getSize(),
                        i.getContentType()
                );

                try {
                    String imageUrl = storage.storeFile(orphanageImagesFolder, imageFileInfo, i.getInputStream());

                    imagesDto.add(
                            new CreateOrphanageImageDto(
                                    imageFileInfo.name(),
                                    imageFileInfo.mimeType(),
                                    imageFileInfo.size(),
                                    imageUrl
                            )
                    );

                } catch (IOException e) {
                    log.error("Error on store image with name: {}", imageFileInfo.name());
                    throw new RuntimeException(e);
                }
            } else {
                log.info("Tried store the empty image file");
            }
        });

        log.info("End the storing of {} Orphanage images", images.size());

        return imagesDto;
    }
}
