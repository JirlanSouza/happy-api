package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.data.models.Orphanage;
import com.dev.happyapi.orphanage.data.models.OrphanageImage;
import com.dev.happyapi.orphanage.data.repositories.OrphanagesRepository;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageDto;
import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.orphanage.exceptions.ExistsEntityException;
import com.dev.happyapi.orphanage.exceptions.NotFoundEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrphanageServices {

    private final OrphanagesRepository repository;
    private final OrphanageImageUploadService imageUploadService;

    public OrphanageServices(OrphanagesRepository repository, OrphanageImageUploadService imageUploadService) {
        this.repository = repository;
        this.imageUploadService = imageUploadService;
    }

    public Orphanage createOrphanage(CreateOrphanageDto orphanageData) {
        if (repository.existsByName(orphanageData.name())) {
            log.warn("An attempt was made to create an orphanage with the name: '{}' that exists", orphanageData.name());

            throw new ExistsEntityException(
                    String.format("The orphanage with name '%s' already exists", orphanageData.name())
            );
        }

        List<CreateOrphanageImageDto> orphanageImagesDto = imageUploadService.saveImages(orphanageData.images());

        var orphanageId = UUID.randomUUID();
        var orphanage = new Orphanage(
                orphanageId,
                orphanageData.name(),
                orphanageData.about(),
                orphanageData.instructions(),
                orphanageData.latitude(),
                orphanageData.longitude(),
                orphanageData.opening_hours(),
                orphanageData.open_on_weekends(),
                orphanageImagesDto.stream().map(
                        i -> new OrphanageImage(
                                UUID.randomUUID(),
                                orphanageId,
                                i.name(),
                                i.size(),
                                i.mimeType(),
                                i.url()
                        )
                ).collect(Collectors.toList())

        );

        log.info("Successful created the orphanage with id: {}", orphanageId);

        return repository.save(orphanage);
    }

    public List<Orphanage> listOrphanages() {
        return repository.findAll();
    }

    public Orphanage getOrphanage(UUID orphanageId) {
        return repository.findById(orphanageId).orElseThrow(
                () -> new NotFoundEntityException("The orphanage with id '%s' do not found")
        );
    }
}
