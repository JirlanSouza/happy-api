package com.dev.happyapi.orphanage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrphanageDto(
    @Size(min = 3)
    String name,

    @Size(max = 500)
    String about,

    @NotNull
    Float latitude,

    @NotNull
    Float longitude,

    @NotNull
    String openingHours,
    boolean openingOnWeekends,

    @NotNull
    List<CreateOrphanageImageDto> images
) {
}

