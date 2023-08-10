package com.dev.happyapi.orphanage.mappers;

import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;

import java.util.UUID;

public class CreateOrphanageImageMapper {
    public static OrphanageImage toEntity(CreateOrphanageImageDto dto, UUID orphanageId) {
        return new OrphanageImage(UUID.randomUUID(), orphanageId, dto.name(), dto.size(), dto.mimeType(), dto.url());
    }
}
