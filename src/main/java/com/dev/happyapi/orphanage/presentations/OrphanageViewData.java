package com.dev.happyapi.orphanage.presentations;

import com.dev.happyapi.orphanage.data.models.Orphanage;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrphanageViewData(
        UUID id,
        String name,
        String about,
        Float latitude,
        Float longitude,
        String openingHours,
        boolean openingOnWeekends,
        List<OrphanageImageViewData> images
) {
    public static OrphanageViewData of(Orphanage orphanage) {
        return new OrphanageViewData(
            orphanage.getId(),
            orphanage.getName(),
            orphanage.getAbout(),
            orphanage.getLatitude(),
            orphanage.getLongitude(),
            orphanage.getOpeningHours(),
            orphanage.isOpeningOnWeekends(),
            orphanage.getImages().stream().map(OrphanageImageViewData::of).collect(Collectors.toList())
        );
    }
}

