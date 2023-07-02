package com.dev.happyapi.orphanage.presentations;

import com.dev.happyapi.orphanage.data.models.OrphanageImage;

import java.util.UUID;

public record OrphanageImageViewData(
        UUID id,
        String name,
        Long size,
        String mimeType,
        String url
) {
    public static OrphanageImageViewData of(OrphanageImage image) {
        return new OrphanageImageViewData(
            image.getId(),
            image.getName(),
            image.getSize(),
            image.getMimeType(),
            image.getUrl()
        );
    }
}
