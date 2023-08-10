package com.dev.happyapi.orphanage.dtos;

import com.dev.happyapi.orphanage.services.interfaces.CreateOrphanageDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public record CreateOrphanageRequest(
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
) implements CreateOrphanageDto {

    @Override
    public String openingHours() {
        return opening_hours();
    }

    @Override
    public boolean openOnWeekends() {
        return open_on_weekends();
    }

    public List<FileDto> imagesFiles() {
        return images.stream().map(
                i -> {
                    try {
                        return new FileDto(
                                i.getOriginalFilename(),
                                i.getContentType(),
                                i.getSize(),
                                i.getInputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();
    }
}

