package com.dev.happyapi.orphanage.mappers;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.services.interfaces.CreateOrphanageDto;

public class CreateOrphanageMapper {
    public static Orphanage toEntity(CreateOrphanageDto dto) {
        return new Orphanage(
                dto.name(),
                dto.about(),
                dto.instructions(),
                dto.latitude(),
                dto.longitude(),
                dto.openingHours(),
                dto.openOnWeekends()
        );
    }
}
