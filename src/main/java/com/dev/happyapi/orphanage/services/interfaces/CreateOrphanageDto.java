package com.dev.happyapi.orphanage.services.interfaces;

import com.dev.happyapi.orphanage.dtos.FileDto;

import java.util.List;

public interface CreateOrphanageDto {
    String name();

    String about();

    String instructions();

    Double latitude();

    Double longitude();

    String openingHours();

    boolean openOnWeekends();

    List<FileDto> imagesFiles();
}
