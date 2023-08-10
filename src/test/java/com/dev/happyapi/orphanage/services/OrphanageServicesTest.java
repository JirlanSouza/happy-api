package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.data.repositories.OrphanagesRepository;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageRequest;
import com.dev.happyapi.orphanage.exceptions.ExistsEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrphanageServicesTest {

    @Mock
    OrphanagesRepository orphanagesRepository;

    @Mock
    OrphanageImageUploadService imageUploadService;

    @Mock
    MultipartFile imageFile;

    @InjectMocks
    OrphanageServices services;

    @Test
    public void shouldThrowsExceptionOnCreateOrphanageWithExistentName() {
        CreateOrphanageRequest dto = createOrphanageDto();
        when(orphanagesRepository.existsByName(dto.name())).thenReturn(true);

        assertThrows(ExistsEntityException.class, () -> services.createOrphanage(dto));

    }

    @Test
    public void shouldCreateAndSaveOrphanage() {
        CreateOrphanageRequest dto = createOrphanageDto();
        when(orphanagesRepository.existsByName(dto.name())).thenReturn(false);
        Orphanage orphanage = createOrphanage(dto);
        when(orphanagesRepository.save(any())).thenReturn(orphanage);

        Orphanage result = services.createOrphanage(dto);

        assertEquals(result.getName(), dto.name());
    }

    @Test
    public void shouldNotCreateOrphanageImageWhenImageFileIsEmpty() {
        CreateOrphanageRequest dto = createOrphanageDto();

        when(imageUploadService.saveImages(any())).thenReturn(Collections.emptyList());
        services.createOrphanage(dto);

        verify(orphanagesRepository).save(argThat(a -> a.getImages().isEmpty()));
    }

    private CreateOrphanageRequest createOrphanageDto() {
        return new CreateOrphanageRequest(
                "Orphanage",
                "Orphanage test",
                "Orphanage test instruction",
                -15.252636,
                15.158563,
                "Orphanage test opening hours",
                false,
                List.of(imageFile)
        );
    }

    private Orphanage createOrphanage(CreateOrphanageRequest dto) {
        Orphanage orphanage = new Orphanage(
                dto.name(),
                dto.about(),
                dto.instructions(),
                dto.latitude(),
                dto.longitude(),
                dto.opening_hours(),
                dto.open_on_weekends()
        );

        orphanage.addImages(dto.images().stream().map(
                i -> createOrphanageImage(i, orphanage.getId())).toList()
        );

        return orphanage;

    }

    private OrphanageImage createOrphanageImage(MultipartFile imageFile, UUID orphanageId) {
        return new OrphanageImage(
                UUID.randomUUID(),
                orphanageId,
                imageFile.getOriginalFilename(),
                imageFile.getSize(),
                imageFile.getContentType(),
                "****"
        );
    }
}