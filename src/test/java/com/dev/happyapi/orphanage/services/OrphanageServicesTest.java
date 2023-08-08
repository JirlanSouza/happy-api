package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.data.repositories.OrphanagesRepository;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageDto;
import com.dev.happyapi.orphanage.exceptions.ExistsEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        CreateOrphanageDto dto = createOrphanageDto();
        Mockito.when(orphanagesRepository.existsByName(dto.name())).thenReturn(true);

        assertThrows(ExistsEntityException.class, () -> services.createOrphanage(dto));

    }

    @Test
    public void shouldCreateAndSaveOrphanage() {
        CreateOrphanageDto dto = createOrphanageDto();
        Mockito.when(orphanagesRepository.existsByName(dto.name())).thenReturn(false);
        Orphanage orphanage = createOrphanage(dto);
        Mockito.when(orphanagesRepository.save(Mockito.any())).thenReturn(orphanage);

        Orphanage result = services.createOrphanage(dto);

        assertEquals(result.getName(), dto.name());
    }

    @Test
    public void shouldNotCreateOrphanageImageWhenImageFileIsEmpty() {
        CreateOrphanageDto dto = createOrphanageDto();

        Mockito.when(imageUploadService.saveImages(Mockito.any())).thenReturn(Collections.emptyList());
        services.createOrphanage(dto);

        Mockito.verify(orphanagesRepository).save(Mockito.argThat(a -> a.getImages().size() == 0));
    }

    private CreateOrphanageDto createOrphanageDto() {
        return new CreateOrphanageDto(
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

    private Orphanage createOrphanage(CreateOrphanageDto dto) {
        UUID id = UUID.randomUUID();

        return new Orphanage(
                id,
                dto.name(),
                dto.about(),
                dto.instructions(),
                dto.latitude(),
                dto.longitude(),
                dto.opening_hours(),
                dto.open_on_weekends(),
                dto.images().stream().map(
                        i -> createOrphanageImage(i, id)).collect(Collectors.toList()
                )
        );
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