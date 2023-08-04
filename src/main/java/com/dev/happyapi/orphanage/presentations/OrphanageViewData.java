package com.dev.happyapi.orphanage.presentations;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrphanageViewData(
        UUID id,
        String name,
        String about,
        Double latitude,
        Double longitude,
        String openingHours,

        @JsonProperty(value = "open_on_weekends")
        boolean openOnWeekends,
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
                orphanage.isOpenOnWeekends(),
                orphanage.getImages().stream().map(OrphanageImageViewData::of).collect(Collectors.toList())
        );
    }
}

