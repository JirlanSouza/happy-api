package com.dev.happyapi.orphanage.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrphanageDto(
    @Size(min = 3)
    String name,

    @Size(max = 500)
    String about,

    @Size(max = 200)
    String instructions,

    @NotNull
    Float latitude,

    @NotNull
    Float longitude,

    @NotNull
    String openingHours,

    @JsonProperty(value = "open_on_weekends")
    boolean openOnWeekends,

    @NotNull
    List<CreateOrphanageImageDto> images
) {
}

