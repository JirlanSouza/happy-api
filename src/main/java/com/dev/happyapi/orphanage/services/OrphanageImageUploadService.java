package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrphanageImageUploadService {
    private final FileStorage storage;

    @Value("storage.orphanages.images.folder_name")
    private String IMAGE_FOLDER;

    public OrphanageImageUploadService(FileStorage storage) {
        this.storage = storage;
    }

    public List<CreateOrphanageImageDto> saveImages(List<MultipartFile> images) {
        List<CreateOrphanageImageDto> imagesDto = new ArrayList<>();

        images.forEach(i -> {
            var imageFileInfo = new FileInfo(
                    i.getOriginalFilename(),
                    i.getSize(),
                    i.getContentType()
            );

            try {
                String imageUrl = storage.storeFile(IMAGE_FOLDER, imageFileInfo, i.getInputStream());

                imagesDto.add(
                        new CreateOrphanageImageDto(
                                imageFileInfo.name(),
                                imageFileInfo.mimeType(),
                                imageFileInfo.size(),
                                imageUrl
                        )
                );

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return imagesDto;
    }
}
