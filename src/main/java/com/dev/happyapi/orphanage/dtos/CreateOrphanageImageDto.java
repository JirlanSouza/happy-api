package com.dev.happyapi.orphanage.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrphanageImageDto(
    @NotBlank
    String name,
    @NotBlank
    @Size(min = 3)
    String mimeType,
    @Max(10 * 1024)
    Long size,
    String url
) {

}
