package com.dev.happyapi.orphanage.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateOrphanageDto(
        @Size(min = 3)
        String name,

        @Size(max = 500)
        String about,

        @Size(max = 200)
        String instructions,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        @NotNull
        String opening_hours,

        boolean open_on_weekends,

        @NotNull
        List<MultipartFile> images
) {
}

